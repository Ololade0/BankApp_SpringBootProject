package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.DepositFundRequest;
import BankApp.App.Bank.dto.request.TransferRequest;
import BankApp.App.Bank.dto.request.WithdrawalFundRequest;
import BankApp.App.Bank.model.TransactionsHistory;



public interface TransactionServices {
    TransactionsHistory generateTransactionHistory(TransactionsHistory transactionsHistory);
    TransactionsHistory createDepositTransaction(DepositFundRequest depositFundRequest);

     TransactionsHistory createWithdrawnTransaction(WithdrawalFundRequest withdrawalFundRequest);
    TransactionsHistory createTransferTransaction(TransferRequest transferRequest);

    TransactionsHistory retrieveTransaction(String transactionId);
}
