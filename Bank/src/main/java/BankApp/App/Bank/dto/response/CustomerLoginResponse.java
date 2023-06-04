package BankApp.App.Bank.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class CustomerLoginResponse {
    private int code;
    private String message;
}
