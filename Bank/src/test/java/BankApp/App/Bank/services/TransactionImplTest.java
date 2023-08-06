package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.GenerateStatementAccountRequest;
import BankApp.App.Bank.model.TransactionsHistory;
import BankApp.App.Bank.model.enums.TransactionType;
import BankApp.App.Bank.repository.TransactionHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


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

    @Test
    public void findAllTransactions() {
        GenerateStatementAccountRequest generateStatementAccountRequest = GenerateStatementAccountRequest.builder()
                .startDate(LocalDate.of(2023,8,6))
                .endDate(LocalDate.of(2023,8,6))
                .build();
       List<TransactionsHistory> allTransactions = transactionServices.retrieveAllTransactions(generateStatementAccountRequest.getStartDate(), generateStatementAccountRequest.getEndDate());
        assertThat(allTransactions).isNotNull();
        System.out.println(allTransactions);


    }





}





