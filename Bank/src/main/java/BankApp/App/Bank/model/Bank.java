package BankApp.App.Bank.model;

import BankApp.App.Bank.model.enums.RoleType;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Setter
@Getter

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Validated
@Document("Bank")
public class Bank {
    @Id
    private String id;
    private String email;

    private String bankLocation;
    @Pattern(regexp = "^.{1,26}$", message = "Name must be between 1 and 26 characters")
    private String bankName;
    @DBRef
    private List<Customer> customers = new ArrayList<>();

    @DBRef
    private Set<Role> roleHashSet = new HashSet<>();

    public Bank(String email, String bankLocation, String bankName, RoleType roleType) {
        this.email = email;
        this.bankLocation = bankLocation;
        this.bankName = bankName;

        if (roleHashSet == null) {
            roleHashSet = new HashSet<>();
            roleHashSet.add(new Role(roleType));


        }
    }
}
