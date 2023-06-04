package BankApp.App.Bank.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountCannotBeFound extends RuntimeException{
    private int statusCode;
    public AccountCannotBeFound(String message) {
        super(message);
    }
    public AccountCannotBeFound(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public AccountCannotBeFound(String accountNumber, String pin) {
    }





    public static String AccountCannotBeFound(String accountId) {
        return "Account with ID " + accountId +  "cannot be found";

    }
}
