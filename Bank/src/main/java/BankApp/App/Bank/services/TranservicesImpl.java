package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.DepositFundRequest;
import BankApp.App.Bank.dto.request.TransferRequest;
import BankApp.App.Bank.dto.request.WithdrawalFundRequest;
import BankApp.App.Bank.exception.TransactionsHistoryCannotBeFound;
import BankApp.App.Bank.model.TransactionsHistory;
import BankApp.App.Bank.model.enums.TransactionType;
import BankApp.App.Bank.repository.TransactionHistoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class TranservicesImpl implements TransactionServices {
    private static int transactionIdCounter;
    @Autowired
    private TransactionHistoryRepository transactionRepository;



    @Override
    public TransactionsHistory generateTransactionHistory(TransactionsHistory transactionsHistory) {
        TransactionsHistory transaction = new TransactionsHistory();
        transaction.setTransactionId(generateTransactionId());
        transaction.setTransactionAmount(transactionsHistory.getTransactionAmount());
        transaction.setDescription(transactionsHistory.getDescription());
        transaction.setTransactionType(transactionsHistory.getTransactionType());
        transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
    }


   @Override
    public TransactionsHistory createDepositTransaction(DepositFundRequest depositFundRequest) {
        TransactionsHistory transactionsHistory = TransactionsHistory.builder()
                .transactionId(generateTransactionId())
                .accountNumber(depositFundRequest.getAccountNumber())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(depositFundRequest.getTransactionAmount())
                .transactionType(TransactionType.CREDIT)
                .description("Deposit of "+ depositFundRequest.getTransactionAmount() + " was made into " + depositFundRequest.getAccountNumber())
                .build();
        transactionRepository.save(transactionsHistory);

        return TransactionsHistory.builder()
                .transactionId(generateTransactionId())
                .accountNumber(depositFundRequest.getAccountNumber())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(depositFundRequest.getTransactionAmount())
                .transactionType(TransactionType.CREDIT)
                .description("Deposit of "+ depositFundRequest.getTransactionAmount() + " was made into " + depositFundRequest.getAccountNumber())
                .build();

    }


    @Override
        public TransactionsHistory createWithdrawnTransaction(WithdrawalFundRequest withdrawalFundRequest) {
        BigDecimal withdrawalAmount = withdrawalFundRequest.getWithdrawalAmount();
        if (withdrawalAmount == null || withdrawalAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }

        TransactionsHistory transactionsHistory = TransactionsHistory.builder()
                .transactionId(generateTransactionId())
                .accountNumber(withdrawalFundRequest.getAccountNumber())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(withdrawalFundRequest.getWithdrawalAmount())
                .transactionType(TransactionType.DEBIT)
                .description("Withdrawal of-" + withdrawalFundRequest.getWithdrawalAmount()+ " made into " + withdrawalFundRequest.getAccountNumber())
                .build();
        transactionRepository.save(transactionsHistory);

        return TransactionsHistory.builder()
                .transactionId(transactionsHistory.getTransactionId())
                 .accountNumber(withdrawalFundRequest.getAccountNumber())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(withdrawalFundRequest.getWithdrawalAmount())
                .transactionType(TransactionType.DEBIT)
                .description("Withdrawal of -"+ withdrawalFundRequest.getWithdrawalAmount() + " was made from " + withdrawalFundRequest.getAccountNumber())
                .build();

    }

    @Override
    public TransactionsHistory createTransferTransaction(TransferRequest transferRequest) {
        TransactionsHistory transactionsHistory = TransactionsHistory.builder()
                .transactionId(generateTransactionId())
                .accountNumber(transferRequest.getReceiverAccountNumber())
                .accountNumber(transferRequest.getSenderAccountNumber())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(transferRequest.getTransactionAmount())
                .transactionType(TransactionType.TRANSFER)
                .description("Transfer of "+ transferRequest.getTransactionAmount() + " was made from" + transferRequest.getSenderAccountNumber() + "to " + transferRequest.getReceiverAccountNumber())
                .build();
        transactionRepository.save(transactionsHistory);

        return TransactionsHistory.builder()
                .transactionId(generateTransactionId())
                .accountNumber(transferRequest.getReceiverAccountNumber())
                .accountNumber(transferRequest.getSenderAccountNumber())
                .transactionDate(LocalDateTime.now())
                .transactionAmount(transferRequest.getTransactionAmount())
                .transactionType(TransactionType.TRANSFER)
                .description("Transfer of "+ transferRequest.getTransactionAmount() + " was made from " + transferRequest.getSenderAccountNumber() + " to " + transferRequest.getReceiverAccountNumber())
                .build();
    }

    @Override
    public TransactionsHistory retrieveTransaction(String transactionId) {
        Optional<TransactionsHistory> foundTransaction = transactionRepository.findById(transactionId);
        return foundTransaction.orElseThrow(() -> {
            throw new TransactionsHistoryCannotBeFound("transaction cannot be found");
        });

       }



    private String generateTransactionId(){
        return String.valueOf(++transactionIdCounter);
    }


}
