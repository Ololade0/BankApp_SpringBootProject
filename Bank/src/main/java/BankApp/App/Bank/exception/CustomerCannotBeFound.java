package BankApp.App.Bank.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerCannotBeFound extends RuntimeException{
    private int statusCode;

    public CustomerCannotBeFound(String message) {
        super(message);
    }
    public CustomerCannotBeFound(String message, int statuscode) {
        super(message);
        this.statusCode = statuscode;
    }

}

