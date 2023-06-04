package BankApp.App.Bank.model;

import BankApp.App.Bank.model.enums.RoleType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Getter
@Setter
@NoArgsConstructor
@ToString
@Document
public class Role {

    @Id
    private String id;

    private RoleType roleType;

    public Role(RoleType roleType) {

        this.roleType = roleType;
    }



}
