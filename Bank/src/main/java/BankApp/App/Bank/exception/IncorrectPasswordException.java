package BankApp.App.Bank.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class IncorrectPasswordException extends RuntimeException {
    private int statusCode;

    public IncorrectPasswordException(String message) {
        super(message)
        ;
    }

    public IncorrectPasswordException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode
        ;
    }

}
