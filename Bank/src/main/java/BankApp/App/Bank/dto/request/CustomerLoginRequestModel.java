package BankApp.App.Bank.dto.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerLoginRequestModel {
    private String email;
    private String password;

}
