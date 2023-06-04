package BankApp.App.Bank.model;

import BankApp.App.Bank.model.enums.AccountType;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Document("Account")
public class Account {
    @Id
    public String accountId;
    private String accountNumber;
    private String accountName;
    private AccountType accountType;
    private String pin;
    private BigDecimal currentBalance;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    @DBRef
    private List<TransactionsHistory> transactions = new ArrayList<>();


    public Account(String accountNumber, BigDecimal currentBalance) {
        this.accountNumber = accountNumber;
        this.currentBalance = currentBalance;
    }
}
