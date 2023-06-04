package BankApp.App.Bank.dto.request;

import BankApp.App.Bank.model.enums.AccountType;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UpdateAccountRequest {
    private String bankId;
    private String accountId;
    private AccountType accountType;
    private String pin;


}
