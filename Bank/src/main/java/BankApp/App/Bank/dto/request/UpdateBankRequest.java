package BankApp.App.Bank.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBankRequest {
    private String bankId;
    private String bankLocation;
    private String bankName;

}
