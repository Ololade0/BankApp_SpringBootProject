package BankApp.App.Bank.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountAmountException extends RuntimeException {
    private int statusCode;
    public AccountAmountException(String message) {
        super(message);
    }
    public AccountAmountException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
