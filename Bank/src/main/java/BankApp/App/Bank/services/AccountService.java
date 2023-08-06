package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.*;
import BankApp.App.Bank.dto.response.DepositFundResponse;
import BankApp.App.Bank.dto.response.TransferFundResponse;
import BankApp.App.Bank.dto.response.WithdrawalFundResponse;
import BankApp.App.Bank.model.Account;

import BankApp.App.Bank.model.TransactionsHistory;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


public interface AccountService {
    Account openAccount(OpenAccountRequest openAccountRequest) throws UnirestException;

    Account findAccountById(String accountId);

    Account findAccountByAccountNumber(String accountNumber);

    Account findAccountByAccountName(String accountName);

    Page<Account> findAllAccount(FindAllAccountRequest findAllAccountRequest);

    String closeAllAccounts();

    long size();

    String closeAccountById(String accountId);


    Account updateAccount(String accountId, UpdateAccountRequest updateAccountRequest);


    WithdrawalFundResponse WithdrawFundFromAccounts(WithdrawalFundRequest withdrawalFundRequest);


    BigDecimal checkAccountBalance(BigDecimal currentBalance, String accountNumber);

    DepositFundResponse depositFundsIntoAccounts(DepositFundRequest depositFundRequest);

    TransferFundResponse transferFunds(TransferRequest transfer);
    List<TransactionsHistory> generateStatmentOfAccount(String accountNumber, LocalDate startDate, LocalDate endDate);

//    List<TransactionsHistory> generateStatementContent(String accountId);
//
//    List<TransactionsHistory> retrieveAllTransactions(String accountId, LocalDate startDate, LocalDate endDate);

//
//    String generateStatementContent(String accountId, LocalDate startDate, LocalDate endDate);
}
