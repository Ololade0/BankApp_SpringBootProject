package BankApp.App.Bank.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class UpdateBankResponse {

    private String message;
    private String bankName;
}
