package BankApp.App.Bank.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankDoesNotExistException extends RuntimeException {
    private int statusCode;


    public  BankDoesNotExistException(String message){

        super(message);
    }

    public  BankDoesNotExistException(String message, int statusCode){

        super(message);
        this.statusCode = statusCode;
    }


}
