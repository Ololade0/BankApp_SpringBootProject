package BankApp.App.Bank.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerRegisterResponse {
    private String bankId;
    private String message;
    private String customerId;
    private String customerName;

}
