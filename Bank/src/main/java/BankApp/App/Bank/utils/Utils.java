package BankApp.App.Bank.utils;

import BankApp.App.Bank.model.enums.AccountType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Setter
@Getter
@ToString
public class Utils {
    private static final int SAVINGS_ACCOUNT = 101;
    private static final int CURRENT_ACCOUNT = 102;
    private static final int BUSINESS_ACCOUNT = 103;
    private static final int STUDENT_ACCOUNT = 104;
    private static final int FIXED_ACCOUNT = 105;
    private static final int ZECA_ACCOUNT = 105;

    public String generateAccountNumber(AccountType accountType) {
        String prefix = String.valueOf(getPrefixForAccountType(accountType));
        int maxDigits = 10 - prefix.length();
        int maxNumber = (int) Math.pow(10, maxDigits) - 1;
        int randomNumber = new Random().nextInt(maxNumber) + 1;
        String number = prefix + String.format("%0" + maxDigits + "d", randomNumber);
        return number;
    }

    private int getPrefixForAccountType(AccountType accountType) {
        return switch (accountType) {
            case CURRENT_ACCOUNT -> 101;
            case SAVINGS_ACCOUNT -> 102;
            case BUSINESS_ACCOUNT -> 103;
            case STUDENT_ACCOUNT -> 104;
            case FIXED_ACCOUNT -> 105;
            default -> throw new IllegalArgumentException("Invalid account type");
        };
    }
        }


