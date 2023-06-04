package BankApp.App.Bank.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransactionsHistoryCannotBeFound extends RuntimeException {
    private int statusCode;
    public TransactionsHistoryCannotBeFound(String message) {
        super(message);
    }
    public TransactionsHistoryCannotBeFound(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
