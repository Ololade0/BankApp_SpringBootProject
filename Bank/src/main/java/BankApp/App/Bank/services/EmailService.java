package BankApp.App.Bank.services;


import BankApp.App.Bank.dto.request.MailRequest;
import BankApp.App.Bank.dto.response.MailResponse;

import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.concurrent.CompletableFuture;

public interface EmailService {
    CompletableFuture<MailResponse> sendSimpleMail(MailRequest mailRequest) throws UnirestException;
}
