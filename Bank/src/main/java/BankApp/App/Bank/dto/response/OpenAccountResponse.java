package BankApp.App.Bank.dto.response;


import BankApp.App.Bank.model.enums.AccountType;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OpenAccountResponse {

    private String accountName;
    private String accountNumber;
    private AccountType accountType;
    private String message;
    private String accountId;

}
