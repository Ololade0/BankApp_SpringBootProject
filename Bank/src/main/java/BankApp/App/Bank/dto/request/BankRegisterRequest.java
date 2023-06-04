package BankApp.App.Bank.dto.request;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BankRegisterRequest {
    private String bankId;
    private String bankName;
    private String email;
    private String banklocation;



}
