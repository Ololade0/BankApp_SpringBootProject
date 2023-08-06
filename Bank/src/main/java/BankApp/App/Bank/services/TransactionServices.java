package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.DepositFundRequest;
import BankApp.App.Bank.dto.request.TransferRequest;
import BankApp.App.Bank.dto.request.WithdrawalFundRequest;
import BankApp.App.Bank.model.TransactionsHistory;

import java.time.LocalDate;
import java.util.List;


public interface TransactionServices {
    TransactionsHistory generateTransactionHistory(TransactionsHistory transactionsHistory);
    TransactionsHistory generateStatementOfAccount(TransactionsHistory transactionsHistory);
    TransactionsHistory createDepositTransaction(DepositFundRequest depositFundRequest);

     TransactionsHistory createWithdrawnTransaction(WithdrawalFundRequest withdrawalFundRequest);
    TransactionsHistory createTransferTransaction(TransferRequest transferRequest);

    TransactionsHistory retrieveTransaction(String transactionId);

    List<TransactionsHistory> retrieveAllTransactions(LocalDate startDate, LocalDate endDate);

//    List<TransactionsHistory> findByAccountIdAndTransactionDateBetween(String accountId, LocalDate startDate, LocalDate endDate);

//    List<TransactionsHistory> retrieveAllTransactions();

//    List<TransactionsHistory> findByAccountIdAndTransactionDateBetween(String accountId, LocalDate startDate, LocalDate endDate);
}
