package BankApp.App.Bank.dto.request;

import jakarta.validation.constraints.Email;
import lombok.*;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailRequest {
    @Email
    private String sender;
    @Email
    private String receiver;
    private String subject;
    private String body;
}
