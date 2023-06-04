package BankApp.App.Bank.model;


import BankApp.App.Bank.model.enums.GenderType;
import BankApp.App.Bank.model.enums.RoleType;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document("Customer")
public class Customer {
    @Id
    private String customerId;
    private String customerEmail;

    @Pattern(regexp = "^.{1,26}$", message = "Name must be between 1 and 26 characters")
    private String customerName;
    private String customerAge;

    private GenderType gender;
    private String password;



    @DBRef
    private List<Account> accounts = new ArrayList<>();

    @DBRef
    private Set<Role> roleHashSet = new HashSet<>();

    public Customer(String customerName, String customerEmail,  String password,String customerAge,
   GenderType gender, RoleType roleType) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerAge = customerAge;
        this.password = password;
        this.gender = gender;
        if (roleHashSet == null) {
            roleHashSet = new HashSet<>();
            roleHashSet.add(new Role(roleType));


        }
    }



}

