package BankApp.App.Bank.repository;

import BankApp.App.Bank.model.TransactionsHistory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionHistoryRepository extends MongoRepository<TransactionsHistory, String> {
//    List<TransactionsHistory> findByTransactionIdAndTransactionDateBetween(LocalDate startDate, LocalDate endDate);
    List<TransactionsHistory> findByAccountNumberAndTransactionDate(LocalDate startDate, LocalDate endDate);

}
