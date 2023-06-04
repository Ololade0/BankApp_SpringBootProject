package BankApp.App.Bank.exception.exceptionHandler;


import BankApp.App.Bank.exception.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerCannotBeFound.class)
    public ResponseEntity<?> handleCustomerCannotBeFoundException(CustomerCannotBeFound customerCannotBeFoundException){
        ApiError apiError = ApiError.builder()
                .message(customerCannotBeFoundException.getMessage())
                .successful(false)
                .statusCode(customerCannotBeFoundException.getStatusCode())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccountAmountException.class)
    public ResponseEntity<?> handleAccountAmountException(AccountAmountException accountAmountException){
        ApiError apiError = ApiError.builder()
                .message(accountAmountException.getMessage())
                .successful(false)
                .statusCode(accountAmountException.getStatusCode())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AccountCannotBeFound.class)
    public ResponseEntity<?> handleAccountCannotBeFoundException(AccountCannotBeFound accountCannotBeFound){
        ApiError apiError = ApiError.builder()
                .message(accountCannotBeFound.getMessage())
                .successful(false)
                .statusCode(accountCannotBeFound.getStatusCode())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }


    @org.springframework.web.bind.annotation.ExceptionHandler(BankDoesNotExistException.class)
    public ResponseEntity<?> handleBankDoesNotExistException(BankDoesNotExistException bankDoesNotExistException){
        ApiError apiError = ApiError.builder()
                .message(bankDoesNotExistException.getMessage())
                .successful(false)
                .statusCode(bankDoesNotExistException.getStatusCode())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BankNameAlreadyExistException.class)
    public ResponseEntity<?> handleBankNameAlreadyExistException(BankNameAlreadyExistException bankNameAlreadyExistException){
        ApiError apiError = ApiError.builder()
                .message(bankNameAlreadyExistException.getMessage())
                .successful(false)
                .statusCode(bankNameAlreadyExistException.getStatusCode())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(IncorrectPasswordException.class)
    public ResponseEntity<?> handleIncorrectPasswordException(IncorrectPasswordException incorrectPasswordException){
        ApiError apiError = ApiError.builder()
                .message(incorrectPasswordException.getMessage())
                .successful(false)
                .statusCode(incorrectPasswordException.getStatusCode())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TransactionsHistoryCannotBeFound.class)
    public ResponseEntity<?> handleTransactionCannotBeFoundException(TransactionsHistoryCannotBeFound transactionsHistoryCannotBeFound){
        ApiError apiError = ApiError.builder()
                .message(transactionsHistoryCannotBeFound.getMessage())
                .successful(false)
                .statusCode(transactionsHistoryCannotBeFound.getStatusCode())
                .build();
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatusCode()));
    }



}
