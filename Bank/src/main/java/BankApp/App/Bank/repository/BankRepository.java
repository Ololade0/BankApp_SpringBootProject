package BankApp.App.Bank.repository;
import BankApp.App.Bank.model.Bank;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BankRepository extends MongoRepository<Bank, String> {
    Bank findBankById(String bankId);
   Optional<Bank> findBankByEmail(String email);

}
