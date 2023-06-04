package BankApp.App.Bank.model;

import BankApp.App.Bank.model.enums.TransactionType;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document("Transaction")
public class TransactionsHistory {
    @Id
    private String transactionId;
    private BigDecimal transactionAmount;
    private String description;
    private String currentBalance;

    private LocalDateTime transactionDate;
    private String accountNumber;
    private TransactionType transactionType;



    public TransactionsHistory(LocalDateTime date, BigDecimal transactionAmount, String accountNumber) {
        this.transactionDate = date;
        this.transactionAmount= transactionAmount;
        this.accountNumber = accountNumber;
    }



}

