package BankApp.App.Bank.services;

import bank.app.virtualbankapp.model.TransactionsHistory;
import bank.app.virtualbankapp.model.enums.TransactionType;
import bank.app.virtualbankapp.repository.TransactionHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@SpringBootTest
class TransactionImplTest {

    @Autowired
    private TransactionServices transactionServices;
    @Autowired
    private TransactionHistoryRepository transactionRepository;
    TransactionsHistory savedTransactions;

    @BeforeEach
    void setUp() {
        TransactionsHistory transactionsHistory = TransactionsHistory.builder()
                .transactionDate(LocalDateTime.now())
                .transactionType(TransactionType.CREDIT)
                .transactionAmount(BigDecimal.valueOf(90000))
                .description("Money for dress")
                .build();
        savedTransactions = transactionServices.generateTransactionHistory(transactionsHistory);


    }

    @AfterEach
    void tearDown() {

        transactionRepository.deleteAll();
    }

    @Test
    public void createTransaction() {
        TransactionsHistory transactionsHistory = TransactionsHistory.builder()
                .transactionDate(LocalDateTime.now())
                .transactionType(TransactionType.CREDIT)
                .transactionAmount(BigDecimal.valueOf(90000))
                .description("Money for dress")
                .build();
        savedTransactions = transactionServices.generateTransactionHistory(transactionsHistory);
        assertThat(savedTransactions).isNotNull();
    }


    @Test
    public void findTransactionById() {
        savedTransactions = transactionServices.retrieveTransaction(savedTransactions.getTransactionId());
        assertThat(savedTransactions).isNotNull();


    }




}





