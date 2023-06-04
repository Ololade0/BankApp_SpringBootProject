package BankApp.App.Bank.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class WithdrawalFundRequest {
    private String bankId;
    private String customerId;

    private String pin;
    private BigDecimal withdrawalAmount;
    private String accountNumber;


}
