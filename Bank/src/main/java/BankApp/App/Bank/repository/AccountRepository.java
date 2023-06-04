package BankApp.App.Bank.repository;


import BankApp.App.Bank.model.Account;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Account findAccountByAccountId(String id);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
    Optional<Account> findAccountByAccountName(String accountName);

}
