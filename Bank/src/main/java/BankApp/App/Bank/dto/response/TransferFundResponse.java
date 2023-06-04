package BankApp.App.Bank.dto.response;

import BankApp.App.Bank.model.TransactionsHistory;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferFundResponse {

    private String messsage;


    private BigDecimal senderCurrentBalance;
    private TransactionsHistory transactionsHistory;
}
