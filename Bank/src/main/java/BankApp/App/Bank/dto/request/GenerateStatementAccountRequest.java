package BankApp.App.Bank.dto.request;

import BankApp.App.Bank.model.enums.GenderType;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GenerateStatementAccountRequest {
    private String bankId;
    private String accountNumber;
    private LocalDate startDate;
    private LocalDate endDate;



}
