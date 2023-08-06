package BankApp.App.Bank.services;


import BankApp.App.Bank.dto.request.*;
import BankApp.App.Bank.dto.response.*;
import BankApp.App.Bank.exception.BankNameAlreadyExistException;
import BankApp.App.Bank.model.Account;
import BankApp.App.Bank.model.Bank;
import BankApp.App.Bank.model.Customer;
import BankApp.App.Bank.model.TransactionsHistory;
import BankApp.App.Bank.model.enums.AccountType;
import BankApp.App.Bank.model.enums.GenderType;
import BankApp.App.Bank.utils.Utils;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

class BankServiceImplTest {

    BankRegisterResponse savedBank;
    CustomerRegisterResponse savedCustomer;

    OpenAccountResponse newAccount;


    @Autowired
    private BankServices bankService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Autowired
    private Utils utils;

    @AfterEach
    void tearDown() {
        bankService.deleteAll();
        bankService.closeAllAccounts();
        bankService.deleteAllCustomers();
    }




    @BeforeEach
    void setUp() throws BankNameAlreadyExistException, UnirestException {

        BankRegisterRequest bankRegisterRequest = new BankRegisterRequest();
        bankRegisterRequest.setEmail("lol@gmail.com");
        bankRegisterRequest.setBankName("Access Bank");
        bankRegisterRequest.setBanklocation("Sabo");
        savedBank = bankService.registerBank(bankRegisterRequest);


        CustomerRegisterRequest customerRegisterRequest = CustomerRegisterRequest.builder()
                .bankId(savedBank.getBankId())
                .customerName("Jummy")
                .customerEmail("Ololadedemilade0909@gmail.com")
                .genderType(GenderType.FEMALE)
                .customerAge("28")
                .customerPassword(bCryptPasswordEncoder.encode("12345"))
                .build();
        savedCustomer = bankService.saveCustomer(customerRegisterRequest);
        System.out.println(savedCustomer);


        OpenAccountRequest openAccountRequest = OpenAccountRequest.builder()
                .bankId(savedBank.getBankId())
                .customerId(savedCustomer.getCustomerId())
                .currentBalance(BigDecimal.valueOf(1000))
                .accountNumber(utils.generateAccountNumber(AccountType.STUDENT_ACCOUNT))
                .accountName(savedCustomer.getCustomerName())
                .accountType(AccountType.STUDENT_ACCOUNT)
                .createdDate(LocalDateTime.now())
                .pin("1234")
                .build();
        newAccount = bankService.openAccount(openAccountRequest, openAccountRequest.getBankId(), openAccountRequest.customerId);




    }

        @Test
    public void bankCanBeCreated() {
        Bank bank = new Bank();
        bank.setBankName("Access Bank");
        bank.setBankLocation("Sabo");
        assertEquals("Access Bank", bank.getBankName());
    }

    @Test
    public void testThatBankCanBeRegister() throws BankNameAlreadyExistException {

        BankRegisterRequest bankRegisterRequest = new BankRegisterRequest();

        savedBank = bankService.registerBank(bankRegisterRequest);

        assertEquals(2, bankService.size());
        assertThat(savedBank.getBankId()).isNotNull();

    }

    @Test
    public void testThatBankCanBeFindById() {
        Bank foundBank = bankService.findBankById(savedBank.getBankId());
        assertThat(foundBank).isNotNull();
        assertThat(foundBank.getId()).isEqualTo(savedBank.getBankId());

    }



    @Test
    public void findAllBank() {
        FindAllBankRequest findAllBankRequest = FindAllBankRequest
                .builder()
                .numberOfPages(3)
                .pageNumber(2)
                .build();
        Page<Bank> bankPage =  bankService.findAllBanks(findAllBankRequest);
        assertThat(bankPage.getTotalElements()).isNotNull();
        assertThat(bankPage.getTotalElements()).isGreaterThan(0);
        assertEquals(1L, bankService.findAllBanks(findAllBankRequest).getTotalElements());


    }


    @Test
    public void deleteBankBYId() {
        bankService.deleteById(savedBank.getBankId());
        assertEquals(0, bankService.size());

    }


    @Test
    public void testThatAllBanksCanBeDeleted() {
     String response =   bankService.deleteAll();
        assertEquals(0, bankService.size());
        assertEquals("All Banks successfully deleted", response);

    }

    @Test
    public void testThatBankProfileCanBeUpdated() {
        UpdateBankRequest updateBankRequest = UpdateBankRequest.builder()
                .bankName("Diamond Bank")
                .bankId(savedBank.getBankId())
                .bankLocation("Lekki")
                .build();
        UpdateBankResponse bank = bankService.updateBankProfile(updateBankRequest);
        assertEquals("Diamond Bank", bank.getBankName());
        assertThat(bank).isNotNull();
    }


    @Test
    public void testThatBankCanSaveCustomer() throws UnirestException {
        CustomerRegisterRequest customerRegisterRequest = new CustomerRegisterRequest();
        customerRegisterRequest.setBankId(savedBank.getBankId());
        savedCustomer = bankService.saveCustomer(customerRegisterRequest);
        assertThat(savedCustomer).isNotNull();
        assertEquals(2, bankService.sizeOfCustomers());

    }

    @Test
    public void testThatBankCanFindCustomerById() {
        Customer foundCustomer = bankService.findCustomerId(savedBank.getBankId(), savedCustomer.getCustomerId());
        assertThat(foundCustomer.getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
    }

    @Test
    public void testThatBankCanDeleteCustomerById() {
        String foundCustomer = bankService.deleteCustomerById(savedBank.getBankId(), savedCustomer.getCustomerId());
        assertEquals("customer successfully deleted", foundCustomer);
    }

    @Test
    public void testThatBankCanFindAllCustomer() {
        FindAllCustomerRequest findAllCustomerRequest = FindAllCustomerRequest.builder()
                .bankId(savedBank.getBankId())
                .pageNumber(1)
                .numberOfPages(2)
                .build();
        Page<Customer> foundCustomer = bankService.findAllCustomers(findAllCustomerRequest);
        System.out.println(foundCustomer.getTotalElements());
        assertThat(foundCustomer.getTotalElements()).isNotNull();
        assertEquals(1L, bankService.findAllCustomers(findAllCustomerRequest).getTotalElements());

    }

    @Test
    public void testThatBankCanDeleteAllCustomers() {
        String customer = bankService.deleteAllCustomers();
        assertEquals(0, bankService.sizeOfCustomers());
        assertEquals("Customer successfully deleted", customer);
    }


    @Test
    public void testThatBankCanUpdateCustomerProfile() {
        UpdateCustomerProfileRequest updateCustomerProfileRequest = new UpdateCustomerProfileRequest();
        updateCustomerProfileRequest.setCustomerName("Demilade");
        updateCustomerProfileRequest.setCustomerEmail("adesuyiololade@gmail.com");
        updateCustomerProfileRequest.setGenderType("MALE");
        updateCustomerProfileRequest.setCustomerAge("45");
        updateCustomerProfileRequest.setCustomerId(savedCustomer.getCustomerId());
        updateCustomerProfileRequest.setBankId(savedBank.getBankId());
        UpdateCustomerProfileResponse response = bankService.updateCustomerProfile(updateCustomerProfileRequest, updateCustomerProfileRequest.getCustomerId());
        assertEquals("Customer with successfully updated", response.getMessage());
        assertEquals("adesuyiololade@gmail.com", response.getCustomerEmail());

    }

    @Test
    public void testThatBankCanOpenAccountForCustomer() throws UnirestException {
        OpenAccountRequest openAccountRequest = OpenAccountRequest.builder()
                .bankId(savedBank.getBankId())
                .customerId(savedCustomer.getCustomerId())
                .currentBalance(BigDecimal.valueOf(1000))
                .accountNumber(utils.generateAccountNumber(AccountType.STUDENT_ACCOUNT))
                .accountName(savedCustomer.getCustomerName())
                .accountType(AccountType.STUDENT_ACCOUNT)
                .createdDate(LocalDateTime.now())
                .pin("1234")
                .build();
        OpenAccountResponse newAccount = bankService.openAccount(openAccountRequest,  openAccountRequest.getBankId(), openAccountRequest.getCustomerId());
        assertThat(newAccount).isNotNull();
        assertEquals("Jummy", newAccount.getAccountName());
//        assertEquals("STUDENT_ACCOUNT", newAccount.getAccountType());
        assertEquals("Account successfully registered", newAccount.getMessage());
        assertThat(newAccount.getAccountId()).isNotNull();

    }

    @Test
    public void testThatBankCanFindAccountById() {
       Account foundAccount = bankService.findAccountById(savedBank.getBankId(), newAccount.getAccountId());
        assertThat(foundAccount.getAccountId()).isNotNull();
        assertThat(foundAccount.accountId).isEqualTo(newAccount.getAccountId());
    }



    @Test
    public void testThatBankCanFindAccountByAccountNumber() {
        Account foundAccount = bankService.findAccountByAccountNumber(savedBank.getBankId(), newAccount.getAccountNumber());
        assertThat(foundAccount.getAccountNumber()).isNotNull();
        assertThat(foundAccount.getAccountNumber()).isEqualTo(newAccount.getAccountNumber());
    }

    @Test
    public void testThatBankCanCloseAccount() {
        String  foundAccount = bankService.closeAccount(savedBank.getBankId(), newAccount.getAccountId());

        assertEquals(0, bankService.sizeOfAccount());
        assertEquals("Account has been successfully closed", foundAccount);
    }




    @Test
    public void withdraw(){
    WithdrawalFundRequest withdrawalFundRequest = new WithdrawalFundRequest();
    withdrawalFundRequest.setBankId(savedBank.getBankId());
    withdrawalFundRequest.setCustomerId(savedCustomer.getCustomerId());
    withdrawalFundRequest.setWithdrawalAmount(BigDecimal.valueOf(100));
    withdrawalFundRequest.setAccountNumber(newAccount.getAccountNumber());
    withdrawalFundRequest.setPin("1234");
    WithdrawalFundResponse withdrawal = bankService.Withdraw(withdrawalFundRequest);
    assertEquals(BigDecimal.valueOf(900), withdrawal.getCurrentBalance());
        assertEquals("Transaction successful", withdrawal.getMessage());


}
@Test
    public void deposit() {
        DepositFundRequest depositFundRequest = new DepositFundRequest();
        depositFundRequest.setCustomerId(savedCustomer.getCustomerId());
        depositFundRequest.setBankId(savedBank.getBankId());
            depositFundRequest.setAccountNumber(newAccount.getAccountNumber());
            depositFundRequest.setTransactionAmount(BigDecimal.valueOf(2000));
        DepositFundResponse depositFunds=
                bankService.depositFunds(depositFundRequest);
        assertEquals(BigDecimal.valueOf(3000), depositFunds.getCurrentBalance());
        assertEquals("Funds successfully deposited", depositFunds.getMessage());

    }

    @Test
    void generateCustomerAccountStatement () {
        GenerateStatementAccountRequest generateStatementAccountRequest = GenerateStatementAccountRequest.builder()
                .accountNumber(newAccount.getAccountNumber())
                .bankId(savedBank.getBankId())
                .startDate(LocalDate.of(2023,8,6))
                .endDate(LocalDate.of(2023,8,6))
                .build();
        List<TransactionsHistory> allTransactions = bankService.generateCustomerStatementOfAccount(savedBank.getBankId(), generateStatementAccountRequest.getAccountNumber(), generateStatementAccountRequest.getStartDate(), generateStatementAccountRequest.getEndDate());
        assertThat(allTransactions).isNotNull();
        System.out.println(allTransactions);
    }


}







