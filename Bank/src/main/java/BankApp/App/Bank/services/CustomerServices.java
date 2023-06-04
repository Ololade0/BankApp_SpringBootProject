package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.CustomerLoginRequestModel;
import BankApp.App.Bank.dto.request.CustomerRegisterRequest;
import BankApp.App.Bank.dto.request.FindAllCustomerRequest;
import BankApp.App.Bank.dto.request.UpdateCustomerProfileRequest;
import BankApp.App.Bank.dto.response.CustomerLoginResponse;
import BankApp.App.Bank.model.Account;
import BankApp.App.Bank.model.Customer;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.data.domain.Page;


public interface CustomerServices {
    Customer saveNewCustomer(CustomerRegisterRequest customerRegister) throws UnirestException;

    long totalNumberOfCustomer();

    Customer findCustomerById(String customerId);
    Customer findCustomerByName(String customerName);

    String deleteAll();

    Page<Customer> findAllCustomers(FindAllCustomerRequest findAllCustomerRequest);

    String deleteCustomerById(String customerId);

    Customer updateCustomerProfile(UpdateCustomerProfileRequest updateCustomerProfileRequest, String customerId);

    Customer findCustomerByEmail(String customerEmail);


    CustomerLoginResponse loginUser(CustomerLoginRequestModel userLoginRequestModel);


   Account findAccountById(String customerId, String accountId);
}


