package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.*;
import BankApp.App.Bank.dto.response.DepositFundResponse;
import BankApp.App.Bank.dto.response.TransferFundResponse;
import BankApp.App.Bank.dto.response.WithdrawalFundResponse;
import BankApp.App.Bank.model.Account;
import BankApp.App.Bank.model.TransactionsHistory;
import BankApp.App.Bank.model.enums.AccountType;
import BankApp.App.Bank.utils.Utils;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {
    Account savedAccount;

    @Autowired

    private AccountService accountService;

    @Autowired

    private Utils utils;





    @BeforeEach
    void setUp() throws UnirestException {
        OpenAccountRequest openAccountRequest = OpenAccountRequest.builder()
                .accountNumber(utils.generateAccountNumber(AccountType.BUSINESS_ACCOUNT))
                .accountType(AccountType.BUSINESS_ACCOUNT)
                .currentBalance(BigDecimal.valueOf(10_000))
                .accountName("Ololade")
                .createdDate(LocalDateTime.now())
                .pin("1234")
                .build();
        savedAccount = accountService.openAccount(openAccountRequest);


    }

    @AfterEach
    void tearDown() {

        accountService.closeAllAccounts();
    }

    @Test
    public void accountCanBeOpen() throws UnirestException {
        OpenAccountRequest openAccountRequest = OpenAccountRequest.builder()
                .accountNumber(utils.generateAccountNumber(AccountType.BUSINESS_ACCOUNT))
                .accountType(AccountType.BUSINESS_ACCOUNT)
                .currentBalance(BigDecimal.valueOf(10_000))
                .accountName("Ololade")
                .pin("1234")
                .createdDate(LocalDateTime.now())
                .build();
        savedAccount = accountService.openAccount(openAccountRequest);
        assertThat(savedAccount).isNotNull();
    }


    @Test
    public void findAccountById() {
        Account foundAccount = accountService.findAccountById(savedAccount.getAccountId());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAccountId()).isEqualTo(savedAccount.getAccountId());

    }

    @Test
    public void findAccountByAccountName() {
        Account foundAccount = accountService.findAccountByAccountName(savedAccount.getAccountName());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAccountName()).isEqualTo(savedAccount.getAccountName());

    }


    @Test
    public void findAccountByAccountNumber() {
        Account foundAccount = accountService.findAccountByAccountNumber(savedAccount.getAccountNumber());
        assertThat(foundAccount).isNotNull();
        assertThat(foundAccount.getAccountNumber()).isEqualTo(savedAccount.getAccountNumber());

    }


    @Test
    public void findAllAccount() {
        FindAllAccountRequest findAllAccountRequest = FindAllAccountRequest.builder()
                .pageNumber(3)
                .numberOfPages(1)
                .build();
        Page<Account> accountPage = accountService.findAllAccount(findAllAccountRequest);
        assertThat(accountPage.getTotalElements()).isNotNull();
        assertEquals(1L, accountService.findAllAccount(findAllAccountRequest).getTotalElements());

    }

    @Test
    public void testThatAllAccountCanBeClosed() {
        accountService.closeAllAccounts();
        assertEquals(0, accountService.size());
    }

    @Test
    public void testThatAccountCanBeClosedById() {
        accountService.closeAccountById(savedAccount.getAccountId());
        assertEquals(0, accountService.size());
    }



    @Test
    public void testThatAccountCanBeUpdated() {
        UpdateAccountRequest updateAccountRequest = UpdateAccountRequest.builder()
                .accountId(savedAccount.getAccountId())
                .accountType(AccountType.STUDENT_ACCOUNT)
                .pin("3455")
                .build();
        Account updatedAccount = accountService.updateAccount(updateAccountRequest.getAccountId(), updateAccountRequest);
        assertThat(updatedAccount.accountId).isNotNull();
        assertThat(updatedAccount.getAccountType().equals(AccountType.STUDENT_ACCOUNT)).toString();


    }



    @Test
    public void customerCanDepositFromAccount() {
        DepositFundRequest depositFundRequest = DepositFundRequest.builder()
                .accountNumber(savedAccount.getAccountNumber())
                .transactionAmount(BigDecimal.valueOf(10000))
                .build();
        DepositFundResponse depositFundResponse = accountService.depositFundsIntoAccounts(depositFundRequest);
        assertEquals("Funds successfully deposited", depositFundResponse.getMessage());
        assertEquals(BigDecimal.valueOf(20000), depositFundResponse.getCurrentBalance());


    }


    @Test
    public void withdrawFromAccount() {
        WithdrawalFundRequest withdrawalFundRequest = WithdrawalFundRequest.builder()
                .withdrawalAmount(BigDecimal.valueOf(1000))
                .accountNumber(savedAccount.getAccountNumber())
                .pin("1234")
                .build();
        WithdrawalFundResponse withdrawal = accountService.WithdrawFundFromAccounts(withdrawalFundRequest);
        assertEquals(BigDecimal.valueOf(9000), withdrawal.getCurrentBalance());
        assertEquals("Transaction successful", withdrawal.getMessage());


    }

    @Test
    public void transferFromAccount() throws UnirestException {
        OpenAccountRequest openAccountRequest = OpenAccountRequest.builder()
                .accountNumber(utils.generateAccountNumber(AccountType.BUSINESS_ACCOUNT))
                .accountType(AccountType.BUSINESS_ACCOUNT)
                .currentBalance(BigDecimal.valueOf(10_000))
                .accountName("Ololade")
                .createdDate(LocalDateTime.now())
                .pin("1234")
                .build();
        Account savedAccount1 = accountService.openAccount(openAccountRequest);

        TransferRequest transferRequest = TransferRequest.builder()
                .transactionAmount(BigDecimal.valueOf(1000))
                .senderAccountNumber(savedAccount.getAccountNumber())
                .receiverAccountNumber(savedAccount1.getAccountNumber())
                .pin("1234")
                .build();
        TransferFundResponse transfer = accountService.transferFunds(transferRequest);
       assertEquals(BigDecimal.valueOf(9000), transfer.getSenderCurrentBalance());

        assertEquals("Transfer successful", transfer.getMesssage());


    }


        @Test
        void checkAccountBalance () {
            BigDecimal balance = accountService.checkAccountBalance(savedAccount.getCurrentBalance(), savedAccount.getAccountNumber());
            assertEquals(BigDecimal.valueOf(10000), balance);
        }

    @Test
    void generateAccountStatement () {
        GenerateStatementAccountRequest generateStatementAccountRequest = GenerateStatementAccountRequest.builder()
                .startDate(LocalDate.of(2023,8,6))
                .endDate(LocalDate.of(2023,8,6))
                .build();
        List<TransactionsHistory> allTransactions = accountService.generateStatmentOfAccount(savedAccount.getAccountNumber(),generateStatementAccountRequest.getStartDate(), generateStatementAccountRequest.getEndDate());
        assertThat(allTransactions).isNotNull();
    }

//    List<TransactionsHistory> generateStatementOfAccount =    accountService.generateStatementContent(savedAccount.getAccountId());
//        assertNotNull(generateStatementOfAccount);
//        System.out.println(generateStatementOfAccount);
//    }
//




}







