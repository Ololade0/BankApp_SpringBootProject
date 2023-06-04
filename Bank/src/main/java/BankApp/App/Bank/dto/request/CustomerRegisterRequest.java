package BankApp.App.Bank.dto.request;

import BankApp.App.Bank.model.enums.GenderType;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CustomerRegisterRequest {
    public String customerId;
    private String bankId;
    private String customerEmail;
    private String customerName;
    private String customerPassword;
    private String customerAge;
    private GenderType genderType;


}
