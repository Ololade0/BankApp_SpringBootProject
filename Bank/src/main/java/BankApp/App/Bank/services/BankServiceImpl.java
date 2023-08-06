package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.*;
import BankApp.App.Bank.dto.response.*;
import BankApp.App.Bank.exception.BankDoesNotExistException;
import BankApp.App.Bank.exception.BankNameAlreadyExistException;
import BankApp.App.Bank.model.*;
import BankApp.App.Bank.model.enums.RoleType;
import BankApp.App.Bank.repository.BankRepository;
import BankApp.App.Bank.repository.RoleRepository;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankServices{
    private final BankRepository bankRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final CustomerServices customerService;
    private final AccountService accountService;



    @Override
    public BankRegisterResponse registerBank(BankRegisterRequest bankRegisterRequest) throws BankNameAlreadyExistException {
        Bank bank = new Bank();
        bank.setEmail(bankRegisterRequest.getEmail());
        bank.setBankName(bankRegisterRequest.getBankName());
        bank.setBankLocation(bankRegisterRequest.getBanklocation());

        Role userRole = new Role(RoleType.MANAGER);
        userRole = roleRepository.save(userRole);
        bank.getRoleHashSet().add(userRole);
        Bank savedBank = bankRepository.save(bank);

        BankRegisterResponse bankRegisterResponse = new BankRegisterResponse();
        bankRegisterResponse.setMessage("Bank successfully registered");
        bankRegisterResponse.setBankId(savedBank.getId());
        bankRegisterResponse.setEmail(savedBank.getEmail());

        bankRegisterResponse.setBankLocation(savedBank.getBankLocation());
        return bankRegisterResponse;

    }

    @Override
    public long size() {
        return bankRepository.count();
    }

    @Override
    public String deleteAll() {
        bankRepository.deleteAll();
        return "All Banks successfully deleted";

    }

    @Override
    public Bank findBankById(String bankId) throws BankDoesNotExistException {
        Bank foundBank = bankRepository.findBankById(bankId);
        if (foundBank != null) {
            return foundBank;
        }
        throw new BankDoesNotExistException("Bank cannot be found");

    }

    @Override
    public Page<Bank> findAllBanks(FindAllBankRequest findAllBankRequest) {
        Pageable pageable = PageRequest.of(findAllBankRequest.getPageNumber() - 1, findAllBankRequest.getNumberOfPages());
        return bankRepository.findAll(pageable);


    }

    @Override
    public String deleteById(String bankId) {
        Bank foundBank = bankRepository.findBankById(bankId);
        if (foundBank != null) {
            bankRepository.deleteById(bankId);
            return "Bank successfully deleted";
        } else throw new BankDoesNotExistException("Bank with Id " + bankId + "does not exist");

    }

    @Override
    public UpdateBankResponse updateBankProfile(UpdateBankRequest updateBankRequest) {
        Bank updatedBank = updateBank(updateBankRequest);
        return UpdateBankResponse.builder()
                .message(updateBankRequest.getBankName() + "Bank successfully updated")
                .bankName(updatedBank.getBankName())
                .build();
    }


    @Override
    public OpenAccountResponse openAccount(OpenAccountRequest openAccountRequest, String bankId, String customerId) throws UnirestException {
        Account newAccount = accountService.openAccount(openAccountRequest);
        Bank foundBank = bankRepository.findBankById(bankId);
        Customer foundCustomer = customerService.findCustomerById(customerId);
        if (foundBank!= null) {
            foundCustomer.getAccounts().add(newAccount);
            bankRepository.save(foundBank);
        }

       return OpenAccountResponse .builder()
                .message("Account successfully registered")
                .accountType(newAccount.getAccountType())
                .accountName(newAccount.getAccountName())
                .accountNumber(newAccount.getAccountNumber())
                .accountId(newAccount.getAccountId())
                .build();
    }


    @Override
    public CustomerRegisterResponse saveCustomer(CustomerRegisterRequest customerRegisterRequest) throws UnirestException {
           var savedCustomer = customerService.saveNewCustomer(customerRegisterRequest);
        Bank foundBank = bankRepository.findBankById(customerRegisterRequest.getBankId());
        Customer foundCustomer = customerService.findCustomerById(customerRegisterRequest.getCustomerId());

        if (foundBank !=null) {
            foundBank.getCustomers().add(savedCustomer);
            bankRepository.save(foundBank);
        }
        CustomerRegisterResponse customerRegisterResponse = new CustomerRegisterResponse();
        customerRegisterResponse.setMessage("Customer successfully registered");
        customerRegisterResponse.setCustomerId(savedCustomer.getCustomerId());
        customerRegisterResponse.setBankId(foundBank.getId());
        customerRegisterResponse.setCustomerName(savedCustomer.getCustomerName());
        return customerRegisterResponse;
    }


    @Override
    public long sizeOfCustomers() {
        return customerService.totalNumberOfCustomer();
    }

    @Override
    public Customer findCustomerId(String bankId, String customerId) {
        Bank foundBank = bankRepository.findBankById(bankId);
        if (foundBank != null) {
            return customerService.findCustomerById(customerId);
        }
        throw new BankDoesNotExistException("Bank cannot be found");
    }

    @Override
    public Page<Customer> findAllCustomers(FindAllCustomerRequest findAllCustomerRequest) {
        Bank foundBank = bankRepository.findBankById(findAllCustomerRequest.getBankId());
        if (foundBank != null) {
            return customerService.findAllCustomers(findAllCustomerRequest);
        }
        throw new BankDoesNotExistException("Bank cannot be found");
}

    @Override
    public String deleteAllCustomers() {
        customerService.deleteAll();
        return "Customer successfully deleted";

    }


    @Override
    public String deleteCustomerById(String bankId, String customerId) {
        Bank foundBank = bankRepository.findBankById(bankId);
        if (foundBank != null) {
            customerService.deleteCustomerById(customerId);
            return "customer successfully deleted";
        }
        else {
            return null;
        }
    }


    @Override
    public UpdateCustomerProfileResponse updateCustomerProfile(UpdateCustomerProfileRequest updateCustomerProfileRequest, String customerId) {
        Customer foundCustomer = customerService.updateCustomerProfile(updateCustomerProfileRequest, customerId);
        Bank foundBank = bankRepository.findBankById(updateCustomerProfileRequest.getBankId());
        if (foundBank != null) {
            foundBank.getCustomers().add(foundCustomer);
            bankRepository.save(foundBank);

        }
        UpdateCustomerProfileResponse updateCustomerProfileResponse = new UpdateCustomerProfileResponse();
        updateCustomerProfileResponse.setMessage("Customer with successfully updated");
        updateCustomerProfileResponse.setCustomerEmail(foundCustomer.getCustomerEmail());

        return updateCustomerProfileResponse;
    }






    @Override
    public long sizeOfAccount() {

        return accountService.size();
    }

    @Override
    public WithdrawalFundResponse Withdraw(WithdrawalFundRequest withdrawalFundRequest) {
       WithdrawalFundResponse withdrawalFundResponse = accountService.WithdrawFundFromAccounts(withdrawalFundRequest);
        Bank foundBank = bankRepository.findBankById(withdrawalFundRequest.getBankId());
        Customer customer =customerService.findCustomerById(withdrawalFundRequest.getCustomerId());
        if(foundBank != null && customer!= null){
        bankRepository.save(foundBank);
        accountService.WithdrawFundFromAccounts(withdrawalFundRequest);
        }
        return WithdrawalFundResponse.builder()
                .currentBalance(withdrawalFundResponse.getCurrentBalance())
                .accountNumber(withdrawalFundRequest.getAccountNumber())
                .message("Transaction successful")
                .transactionsHistory(withdrawalFundResponse.getTransactionsHistory())
                .build();

    }

    @Override
    public DepositFundResponse depositFunds(DepositFundRequest depositFundRequest) {
        DepositFundResponse depositFundResponse = accountService.depositFundsIntoAccounts(depositFundRequest);
        Bank foundBank = bankRepository.findBankById(depositFundRequest.getBankId());
        Customer customer = customerService.findCustomerById(depositFundRequest.getCustomerId());
        if (foundBank != null && customer != null) {

            accountService.depositFundsIntoAccounts(depositFundRequest);
            bankRepository.save(foundBank);

        }
        return DepositFundResponse.builder()
                .currentBalance(depositFundResponse.getCurrentBalance())
                .accountNumber(depositFundResponse.getAccountNumber())
                .message("Funds successfully deposited")
                .transactionsHistory(depositFundResponse.getTransactionsHistory())
                .build();
    }


    @Override
    public Account findAccountById(String bankId, String accountId) {
       if(bankRepository.findBankById(bankId) != null){
           return accountService.findAccountById(accountId);
       }
       else {
           return null;
       }
    }

    @Override
    public Account findAccountByAccountNumber(String bankId, String accountNumber) {
        if(bankRepository.findBankById(bankId) != null){
            return accountService.findAccountByAccountNumber(accountNumber);
        }
        else {
            return null;
        }

    }

    @Override
    public Account findAccountByAccountName(String bankId, String accountName) {
        if(bankRepository.findBankById(bankId) != null){
            return accountService.findAccountByAccountName(accountName);
        }
        else {
            return null;
        }

    }



    @Override
    public String closeAllAccounts() {
        accountService.closeAllAccounts();
        return "All Account successfully closed";
    }

    @Override
    public String closeAccount(String bankId, String accountId) {
       Bank foundBank = bankRepository.findBankById(bankId);
       if(foundBank!=null){
           accountService.closeAccountById(accountId);
           return "Account has been successfully closed";
       }
       else {
           throw new BankDoesNotExistException("Bank cannot be found");
       }
    }



    @Override
    public Optional<Bank> findBankByEmail(String email) {
        if(bankRepository.findBankByEmail(email).isPresent()){
            return bankRepository.findBankByEmail(email);
        }
        else {
            return Optional.empty();
        }
    }


    private Bank updateBank(UpdateBankRequest updateBankRequest) {
        Bank foundBank = bankRepository.findBankById(updateBankRequest.getBankId());
        if (foundBank != null) {
            foundBank.setBankName(updateBankRequest.getBankName());
            foundBank.setBankLocation(updateBankRequest.getBankLocation());
            return bankRepository.save(foundBank);
        }
        throw new BankDoesNotExistException("Bank cannot be found");
    }

    @Override
    public List<TransactionsHistory> generateCustomerStatementOfAccount(String bankId, String accountNumber, LocalDate startDate, LocalDate endDate) {
        return accountService.generateStatmentOfAccount(accountNumber, startDate, endDate);
    }


}




