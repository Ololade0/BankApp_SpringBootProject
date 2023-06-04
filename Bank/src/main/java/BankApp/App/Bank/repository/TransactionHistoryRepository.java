package BankApp.App.Bank.repository;

import BankApp.App.Bank.model.TransactionsHistory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryRepository extends MongoRepository<TransactionsHistory, String> {
}
