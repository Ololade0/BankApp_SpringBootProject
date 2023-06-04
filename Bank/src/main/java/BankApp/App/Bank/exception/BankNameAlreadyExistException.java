package BankApp.App.Bank.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankNameAlreadyExistException extends RuntimeException{
    private String message;
    private int statusCode;

    public BankNameAlreadyExistException(String message,  int statusCode) {
        super(message);

        this.statusCode = statusCode;
    }
}
