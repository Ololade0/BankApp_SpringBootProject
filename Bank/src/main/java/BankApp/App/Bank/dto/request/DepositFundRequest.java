package BankApp.App.Bank.dto.request;

import BankApp.App.Bank.model.TransactionsHistory;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class DepositFundRequest {
    private String bankId;
    private String customerId;
    private String accountNumber;
    private BigDecimal transactionAmount;
    private List<TransactionsHistory> transactionsHistories;

}
