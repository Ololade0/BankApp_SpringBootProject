package BankApp.App.Bank.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class FindAllCustomerRequest {
    private int numberOfPages;
    private int pageNumber;

    private String bankId;
//   // private String customerName;
}
