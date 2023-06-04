package BankApp.App.Bank.repository;

import BankApp.App.Bank.model.Customer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface CustomerRepository extends MongoRepository<Customer, String> {
    Customer findCustomerByCustomerId(String id);
    Customer findCustomerByCustomerName(String customerName);

    Optional<Customer> findCustomerByCustomerEmail(String customerEmail);



//

}
