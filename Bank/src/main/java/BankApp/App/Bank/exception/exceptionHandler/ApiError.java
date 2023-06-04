package BankApp.App.Bank.exception.exceptionHandler;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private String message;
    private int statusCode;
    private boolean successful;

}
