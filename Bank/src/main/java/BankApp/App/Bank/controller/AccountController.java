package BankApp.App.Bank.controller;

import BankApp.App.Bank.dto.request.DepositFundRequest;
import BankApp.App.Bank.dto.request.OpenAccountRequest;
import BankApp.App.Bank.dto.request.TransferRequest;
import BankApp.App.Bank.dto.request.WithdrawalFundRequest;
import BankApp.App.Bank.dto.response.DepositFundResponse;
import BankApp.App.Bank.dto.response.TransferFundResponse;
import BankApp.App.Bank.dto.response.WithdrawalFundResponse;
import BankApp.App.Bank.model.Account;
import BankApp.App.Bank.services.AccountService;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/accounts")
    public ResponseEntity<?> openAccount(@RequestBody OpenAccountRequest openAccountRequest) throws UnirestException {
        Account accountResponse  = accountService.openAccount(openAccountRequest);
        return new ResponseEntity<>(accountResponse, HttpStatus.CREATED);
    }
    @GetMapping("{accountId}/accountId")
    public ResponseEntity<?>findAccountById( @PathVariable String accountId){
        Account foundAccount =   accountService.findAccountById(accountId);
        return new ResponseEntity<>(foundAccount, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}/accountNumber")
    public ResponseEntity<?>findAccountByAccountNumber(@PathVariable String accountNumber){
        Account foundAccount =   accountService.findAccountByAccountNumber(accountNumber);
        return new ResponseEntity<>(foundAccount, HttpStatus.CREATED);
    }

    @GetMapping("{accountName}/accountName")
    public ResponseEntity<?>findAccountByAccountName(@PathVariable String accountName){
        Account foundAccount =   accountService.findAccountByAccountName(accountName);
        return new ResponseEntity<>(foundAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("{accountId}/closeAccount")
    public ResponseEntity<?>closeAccountById(@PathVariable String accountId){
        String closeAccount =   accountService.closeAccountById(accountId);
        return new ResponseEntity<>(closeAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("closeAllAccounts")
    public ResponseEntity<?>closeAllAccounts(){
        String closeAccount =   accountService.closeAllAccounts();
        return new ResponseEntity<>(closeAccount, HttpStatus.CREATED);
    }
    @PostMapping("account/depositFund")
    public ResponseEntity<?> depositFunds(@RequestBody DepositFundRequest depositFundRequest){
        DepositFundResponse depositResponse  = accountService.depositFundsIntoAccounts(depositFundRequest);
        return new ResponseEntity<>(depositResponse, HttpStatus.CREATED);
    }

    @PostMapping("account/withdrawFund")
    public ResponseEntity<?> withdrawFunds(@RequestBody WithdrawalFundRequest withdrawalFundRequest){
        WithdrawalFundResponse depositResponse  = accountService.WithdrawFundFromAccounts(withdrawalFundRequest);
        return new ResponseEntity<>(depositResponse, HttpStatus.CREATED);
    }

    @PostMapping("account/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferRequest transferRequest){
        TransferFundResponse transferFundResponse  = accountService.transferFunds(transferRequest);
        return new ResponseEntity<>(transferFundResponse, HttpStatus.CREATED);
    }
}
