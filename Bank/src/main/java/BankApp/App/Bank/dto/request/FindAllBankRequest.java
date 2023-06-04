package BankApp.App.Bank.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindAllBankRequest {
    private int numberOfPages;
    private int pageNumber;
}
