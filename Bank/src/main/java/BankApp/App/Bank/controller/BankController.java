package BankApp.App.Bank.controller;

import BankApp.App.Bank.dto.request.*;
import BankApp.App.Bank.dto.response.*;
import BankApp.App.Bank.model.Account;
import BankApp.App.Bank.model.Bank;
import BankApp.App.Bank.model.Customer;
import BankApp.App.Bank.services.BankServices;

import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class BankController {

    private final BankServices bankServices;


    @PostMapping("/bank")
    public ResponseEntity<?> registerBank(@Valid @RequestBody BankRegisterRequest bankRegisterRequest){
       BankRegisterResponse createdBank  = bankServices.registerBank(bankRegisterRequest);
        return new ResponseEntity<>(createdBank, HttpStatus.CREATED);
    }



    @GetMapping("/{bankId}")
    public ResponseEntity<?> findBankById(@PathVariable String bankId){
        Bank foundBank  = bankServices.findBankById(bankId);
        return new ResponseEntity<>(foundBank, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAllBank(@RequestBody FindAllBankRequest findAllBankRequest) {
        Page<Bank> savedBanks = bankServices.findAllBanks(findAllBankRequest);
        return new ResponseEntity<>(savedBanks, HttpStatus.CREATED);
    }


    @DeleteMapping()
    public ResponseEntity<?> deleteAllBanks(){
        String deletedBank  = bankServices.deleteAll();
        return new ResponseEntity<>(deletedBank, HttpStatus.CREATED);
    }

    @DeleteMapping("{/banksId}")
    public ResponseEntity<?> deleteBankById(@PathVariable String banksId){
        String deletedBank  = bankServices.deleteById(banksId);
        return new ResponseEntity<>(deletedBank, HttpStatus.CREATED);
    }
    @PutMapping("/updateBank")
    public ResponseEntity<?> updateBankProfile(@RequestBody UpdateBankRequest updateBankRequest){
        UpdateBankResponse updatedBank  = bankServices.updateBankProfile(updateBankRequest);
        return new ResponseEntity<>(updatedBank, HttpStatus.CREATED);
    }

    @PostMapping("/account")
    public ResponseEntity<?> openAccount(@RequestBody OpenAccountRequest openAccountRequest, String bankId, String customerId) throws UnirestException {
        OpenAccountResponse accountResponse  = bankServices.openAccount(openAccountRequest,  bankId, customerId);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }
    @GetMapping("/{bankId}/{accountId}/accountId")
    public ResponseEntity<?>findAccountById(@PathVariable String bankId,@PathVariable String accountId){
     Account foundAccount =   bankServices.findAccountById(bankId, accountId);
        return new ResponseEntity<>(foundAccount, HttpStatus.CREATED);

    }

    @GetMapping("/{bankId}/{accountNumber}/accountNumber")
    public ResponseEntity<?>findAccountByAccountNumber(@PathVariable String bankId,@PathVariable String accountNumber){
        Account foundAccount =   bankServices.findAccountByAccountNumber(bankId,accountNumber);
        return new ResponseEntity<>(foundAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{bankId}/{accountName}/accountName")
    public ResponseEntity<?>findAccountByAccountName(@PathVariable String bankId,@PathVariable String accountName){
        Account foundAccount =   bankServices.findAccountByAccountName(bankId,accountName);
        return new ResponseEntity<>(foundAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bankId}/{accountId}/closeAccount")
//    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<?>closeAccount(@PathVariable String bankId,@PathVariable String accountId){
        String foundAccount =   bankServices.closeAccount(bankId,accountId);
        return new ResponseEntity<>(foundAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("closeAllAccount")
//    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<?>closeAllAccount(){
        String foundAccount =   bankServices.closeAllAccounts();
        return new ResponseEntity<>(foundAccount, HttpStatus.CREATED);
    }

    @PostMapping("/depositFund")
    public ResponseEntity<?> depositFunds(@RequestBody DepositFundRequest depositFundRequest){
        DepositFundResponse depositResponse  = bankServices.depositFunds(depositFundRequest);
        return new ResponseEntity<>(depositResponse, HttpStatus.CREATED);
    }

    @PostMapping("withdrawFund")
    public ResponseEntity<?> withdrawFunds(@RequestBody WithdrawalFundRequest withdrawalFundRequest){
        WithdrawalFundResponse depositResponse  = bankServices.Withdraw(withdrawalFundRequest);
        return new ResponseEntity<>(depositResponse, HttpStatus.CREATED);
    }

    @PostMapping("/customer")
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerRegisterRequest customerRegisterRequest) throws UnirestException {
        CustomerRegisterResponse saveCustomer  = bankServices.saveCustomer(customerRegisterRequest);
        return new ResponseEntity<>(saveCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/{bankId}/{customerId}")
    public ResponseEntity<?> findCustomerById(@PathVariable String bankId, @PathVariable String customerId){
        Customer foundCustomer  = bankServices.findCustomerId(bankId, customerId);
        return new ResponseEntity<>(foundCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/findAllCustomer")
    public ResponseEntity<?> findAllCustomer(@RequestBody FindAllCustomerRequest findAllCustomerRequest){
        Page<Customer>foundCustomer  = bankServices.findAllCustomers(findAllCustomerRequest);
        return new ResponseEntity<>(foundCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAllCustomer")
    public ResponseEntity<?> deleteAllCustomer(){
        String deletedCustomer  = bankServices.deleteAllCustomers();
        return new ResponseEntity<>(deletedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{bankId}/{customerId}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable String bankId, @PathVariable String customerId){
        String deletedCustomer  = bankServices.deleteCustomerById(bankId, customerId);
        return new ResponseEntity<>(deletedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}/update")
    public ResponseEntity<?> updateCustomerProfile(@RequestBody UpdateCustomerProfileRequest updateCustomerProfileRequest, @PathVariable String customerId){
        UpdateCustomerProfileResponse updateCustomerProfileResponse   = bankServices.updateCustomerProfile(updateCustomerProfileRequest, customerId);
        return new ResponseEntity<>(updateCustomerProfileResponse, HttpStatus.CREATED);
    }



}
