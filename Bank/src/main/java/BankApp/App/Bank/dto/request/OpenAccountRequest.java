package BankApp.App.Bank.dto.request;

import BankApp.App.Bank.model.enums.AccountType;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OpenAccountRequest {
//    private Account account;
    public String customerId;
    private String bankId;
    private String accountNumber;
    private String accountName;

    private BigDecimal currentBalance;
    private String pin;
    private AccountType accountType;
    private LocalDateTime createdDate;



}
