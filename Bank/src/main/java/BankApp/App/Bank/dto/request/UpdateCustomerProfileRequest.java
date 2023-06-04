package BankApp.App.Bank.dto.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerProfileRequest {
    private String customerId;
    private String bankId;
    private String customerEmail;

    private String customerName;
    private String customerAge;
    private String genderType;
}
