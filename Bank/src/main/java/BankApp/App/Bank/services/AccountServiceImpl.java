package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.*;
import BankApp.App.Bank.dto.response.DepositFundResponse;
import BankApp.App.Bank.dto.response.TransferFundResponse;
import BankApp.App.Bank.dto.response.WithdrawalFundResponse;
import BankApp.App.Bank.exception.AccountAmountException;
import BankApp.App.Bank.exception.AccountCannotBeFound;
import BankApp.App.Bank.exception.IncorrectPasswordException;
import BankApp.App.Bank.model.Account;
import BankApp.App.Bank.model.TransactionsHistory;
import BankApp.App.Bank.repository.AccountRepository;
import BankApp.App.Bank.utils.Utils;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;



@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionServices transactionServices;

    @Autowired
    private Utils utils;

    @Autowired
    private EmailService emailService;
    String accountNumber;


//    private static int transactionIdCounter;



    @Override
    public Account openAccount(OpenAccountRequest openAccountRequest) throws UnirestException {
        if (openAccountRequest.getAccountType() == null) {
            throw new AccountCannotBeFound("AccountType cannot be null");
        }
       accountNumber = utils.generateAccountNumber(openAccountRequest.getAccountType());
        Account newAccount = Account.builder()
                .accountNumber(accountNumber)
                .accountType(openAccountRequest.getAccountType())
                .accountName(openAccountRequest.getAccountName())
                .pin(openAccountRequest.getPin())
                .currentBalance(openAccountRequest.getCurrentBalance())
                .createdDate(LocalDateTime.now())
                .build();
//        sendMail(openAccountRequest);
        return accountRepository.save(newAccount);
    }



    private void sendMail(OpenAccountRequest openAccountRequest) throws UnirestException {
        MailRequest mailRequest = MailRequest.builder()
                .sender(System.getenv("SENDER"))
                .receiver(openAccountRequest.getAccountName())
                .subject("You are welcome")
                .body("Hello " + openAccountRequest.getAccountName() + ". Account successfully opened" +  " Your account number is :" + accountNumber)
                .build();
        emailService.sendSimpleMail(mailRequest);

    }

    @Override
    public Account findAccountById(String accountId) {
        Optional<Account> foundAccount = accountRepository.findById(accountId);
        return foundAccount.orElseThrow(() -> {
            throw new AccountCannotBeFound("account cannot be found");
        });
    }

    @Override
    public Account findAccountByAccountNumber(String accountNumber) {
        Optional<Account> foundAccount = accountRepository.findAccountByAccountNumber(accountNumber);
        return foundAccount.orElseThrow(() -> {
            throw new AccountCannotBeFound("account cannot be found");
        });
    }

    @Override
    public Account findAccountByAccountName(String accountName) {
        Optional<Account> foundAccount = accountRepository.findAccountByAccountName(accountName);
        return foundAccount.orElseThrow(() -> {
            throw new AccountCannotBeFound("account cannot be found");
        });

    }


    @Override
    public Page<Account> findAllAccount(FindAllAccountRequest findAllAccountRequest) {
        Pageable pageable = PageRequest.of(findAllAccountRequest.getPageNumber() - 1, findAllAccountRequest.getNumberOfPages());
        return accountRepository.findAll(pageable);
    }

    @Override
    public String closeAllAccounts() {
        accountRepository.deleteAll();
        return "All Accounts successfully closed";
    }


    @Override
    public String closeAccountById(String accountId) {
        Optional<Account> foundAccount = accountRepository.findById(accountId);
        if (foundAccount.isPresent()) {
            accountRepository.deleteById(accountId);
            return "Account successfully closed";
        }
        else {
            throw new AccountCannotBeFound("Account cannot be found");
        }

    }



    @Override
    public Account updateAccount(String accountId, UpdateAccountRequest updateAccountRequest) {
        Account foundAccount = accountRepository.findAccountByAccountId(accountId);
        if (foundAccount != null) {
            foundAccount.setAccountType(updateAccountRequest.getAccountType());
            foundAccount.setPin(updateAccountRequest.getPin());
            foundAccount.setUpdatedDate(LocalDateTime.now());

            return accountRepository.save(foundAccount);
        } else throw new AccountCannotBeFound("Account cannot be found");
    }


    @Override
    public WithdrawalFundResponse WithdrawFundFromAccounts(WithdrawalFundRequest withdrawalFundRequest) {
        Optional<Account> foundAccount = accountRepository.findAccountByAccountNumber(withdrawalFundRequest.getAccountNumber());
        if (foundAccount.isEmpty()) {
            throw new AccountCannotBeFound("Account not found");
        }

        if (foundAccount.get().getCurrentBalance().compareTo(withdrawalFundRequest.getWithdrawalAmount()) < 0) {
            throw new AccountAmountException("Insufficient balance");
        }

        if (!foundAccount.get().getPin().equals(withdrawalFundRequest.getPin())) {
            throw new IncorrectPasswordException("Invalid Pin");
        }
        else {

            foundAccount.get().setCurrentBalance(foundAccount.get().getCurrentBalance().subtract(withdrawalFundRequest.getWithdrawalAmount()));
            foundAccount.get().setUpdatedDate(LocalDateTime.now());
            TransactionsHistory debitTransaction = transactionServices.createWithdrawnTransaction(withdrawalFundRequest);
            foundAccount.get().getTransactions().add(debitTransaction);
            accountRepository.save(foundAccount.get());
            return WithdrawalFundResponse.builder()
                    .currentBalance(foundAccount.get().getCurrentBalance())
                    .accountNumber(foundAccount.get().getAccountNumber())
                    .message("Transaction successful")
                    .transactionsHistory(debitTransaction)
                    .build();
        }
    }



    @Override
    public BigDecimal checkAccountBalance(BigDecimal currentBalance, String accountNumber) {
        Optional<Account> foundAccount = accountRepository.findAccountByAccountNumber(accountNumber);
        if(foundAccount.isPresent()){
            return foundAccount.get().getCurrentBalance();
        }
        else throw new AccountCannotBeFound("Account Cannot be found");
    }


    @Override
    public DepositFundResponse depositFundsIntoAccounts(DepositFundRequest depositFundRequest) {
        Optional<Account> foundAccount = accountRepository.findAccountByAccountNumber(depositFundRequest.getAccountNumber());
        if (foundAccount.isPresent()) {
            if (depositFundRequest.getTransactionAmount().compareTo(BigDecimal.ZERO) <= 0) {
                throw new AccountAmountException("Transaction amount must be greater than 0");
            } else {
                foundAccount.get().setCurrentBalance(foundAccount.get().getCurrentBalance().add(depositFundRequest.getTransactionAmount()));
                foundAccount.get().setUpdatedDate(LocalDateTime.now());
                accountRepository.save(foundAccount.get());

                TransactionsHistory creditTransaction = transactionServices.createDepositTransaction(depositFundRequest) ;
                foundAccount.get().getTransactions().add(creditTransaction);
                accountRepository.save(foundAccount.get());

                return DepositFundResponse.builder()
                        .currentBalance(foundAccount.get().getCurrentBalance())
                        .accountNumber(foundAccount.get().getAccountNumber())

                        .message("Funds successfully deposited")
                        .transactionsHistory(creditTransaction)
                        .build();
            }
        }
        else {
            throw new AccountCannotBeFound("Account does not exist");
        }
    }


//






    @Override
    public long size() {

        return accountRepository.count();
    }



    @Override
    @Transactional
    public TransferFundResponse transferFunds(TransferRequest transfer) {
        Account accountFrom = findAccountByAccountNumber(transfer.getSenderAccountNumber());
        Account accountTo = findAccountByAccountNumber(transfer.getReceiverAccountNumber());
        if(accountFrom.getCurrentBalance().compareTo(transfer.getTransactionAmount()) < 0) {
            throw new AccountAmountException("Account with number:" + accountFrom.getAccountNumber() + " does not have enough balance to transfer.");
        }

        if (!accountFrom.getPin().equals(transfer.getPin())) {
            throw new IncorrectPasswordException("Invalid Pin");
        }

        accountFrom.setCurrentBalance(accountFrom.getCurrentBalance().subtract(transfer.getTransactionAmount()));
        accountTo.setCurrentBalance(accountTo.getCurrentBalance().add(transfer.getTransactionAmount()));
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        TransactionsHistory transferTransaction = transactionServices.createTransferTransaction(transfer) ;
        accountTo.getTransactions().add(transferTransaction);
        accountFrom.getTransactions().add(transferTransaction);
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        return TransferFundResponse.builder()
                .messsage("Transfer successful")
                .senderCurrentBalance(accountFrom.getCurrentBalance())
                .transactionsHistory(transferTransaction)
                .build();

    }












}




