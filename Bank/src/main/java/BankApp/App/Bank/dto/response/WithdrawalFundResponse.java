package BankApp.App.Bank.dto.response;

import BankApp.App.Bank.model.TransactionsHistory;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class WithdrawalFundResponse {
        private String message;
        private String accountNumber;
        private BigDecimal currentBalance;
        private TransactionsHistory transactionsHistory;


    }
