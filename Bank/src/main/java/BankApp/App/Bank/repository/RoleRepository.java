package BankApp.App.Bank.repository;

import BankApp.App.Bank.model.Role;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
