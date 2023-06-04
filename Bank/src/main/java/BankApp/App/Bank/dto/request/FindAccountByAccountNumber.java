package BankApp.App.Bank.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAccountByAccountNumber {
    private String bankId;
    private String accountNumber;
}
