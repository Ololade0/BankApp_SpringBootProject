package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.*;
import BankApp.App.Bank.dto.response.*;
import BankApp.App.Bank.model.Account;
import BankApp.App.Bank.model.Bank;


import BankApp.App.Bank.model.Customer;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.data.domain.Page;

import java.util.Optional;


public interface BankServices {
    BankRegisterResponse registerBank(BankRegisterRequest bankRegisterRequest);

    long size();

    String deleteAll();

    Bank findBankById(String bankId);

    Page<Bank> findAllBanks(FindAllBankRequest findAllBankRequest);

    String deleteById(String bankId);

    UpdateBankResponse updateBankProfile(UpdateBankRequest updateBankRequest);

    CustomerRegisterResponse saveCustomer(CustomerRegisterRequest customerRegisterRequest) throws UnirestException;

    long sizeOfCustomers();

    Customer findCustomerId(String bankId, String customerId);

    Page<Customer> findAllCustomers(FindAllCustomerRequest findAllCustomerRequest);

    String deleteAllCustomers();

    UpdateCustomerProfileResponse updateCustomerProfile(UpdateCustomerProfileRequest updateCustomerProfileRequest, String customerId);

    OpenAccountResponse openAccount(OpenAccountRequest openAccountRequest, String bankId, String customerName) throws UnirestException;

    long sizeOfAccount();

    WithdrawalFundResponse Withdraw(WithdrawalFundRequest withdrawalFundRequest);


    DepositFundResponse depositFunds(DepositFundRequest depositFundRequest);

    String deleteCustomerById(String bankId, String customerId);

    Account findAccountById(String bankId, String accountId);

    Account findAccountByAccountNumber(String bankId, String accountNumber);

    Account findAccountByAccountName(String bankId, String accountName);


    String closeAllAccounts();

    String closeAccount(String bankId, String accountId);

    Optional<Bank> findBankByEmail(String email);
}
