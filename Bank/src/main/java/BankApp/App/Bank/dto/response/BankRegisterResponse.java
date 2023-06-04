package BankApp.App.Bank.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BankRegisterResponse {
    private String message;
    private String bankId;
    private String email;

    private String bankLocation;


}
