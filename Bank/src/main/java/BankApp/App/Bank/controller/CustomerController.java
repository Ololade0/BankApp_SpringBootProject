package BankApp.App.Bank.controller;

import BankApp.App.Bank.dto.request.CustomerLoginRequestModel;
import BankApp.App.Bank.dto.request.CustomerRegisterRequest;
import BankApp.App.Bank.dto.request.FindAllCustomerRequest;
import BankApp.App.Bank.dto.request.UpdateCustomerProfileRequest;
import BankApp.App.Bank.model.AuthToken;
import BankApp.App.Bank.model.Customer;
import BankApp.App.Bank.security.jwt.TokenProvider;
import BankApp.App.Bank.services.CustomerServices;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class CustomerController {
    private final CustomerServices customerServices;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;


    @PostMapping("/customers")
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerRegisterRequest customerRegisterRequest) throws UnirestException {
        Customer saveCustomer  = customerServices.saveNewCustomer(customerRegisterRequest);
        return new ResponseEntity<>(saveCustomer, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody CustomerLoginRequestModel loginRequest)  {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                        loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = tokenProvider.generateJWTToken(authentication);
        Customer customer = customerServices.findCustomerByEmail(loginRequest.getEmail());
        return new ResponseEntity<>(new AuthToken(token, customer.getCustomerId()), HttpStatus.OK);
    }

    @GetMapping("{customerId}/customerId")
    public ResponseEntity<?> findCustomerById(@PathVariable String customerId){
        Customer foundCustomer  = customerServices.findCustomerById(customerId);
        return new ResponseEntity<>(foundCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/findAllCustomers")
    public ResponseEntity<?> findAllCustomer(@RequestBody FindAllCustomerRequest findAllCustomerRequest){
        Page<Customer> foundCustomer  = customerServices.findAllCustomers(findAllCustomerRequest);
        return new ResponseEntity<>(foundCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteAllCustomers")
    public ResponseEntity<?> deleteAllCustomer(){
        String deletedCustomer  = customerServices.deleteAll();
        return new ResponseEntity<>(deletedCustomer, HttpStatus.CREATED);
    }

    @DeleteMapping("/{customerId}/deletecustomers")
    public ResponseEntity<?> deleteCustomerById( @PathVariable String customerId){
        return new ResponseEntity<>(customerServices.deleteCustomerById(customerId), HttpStatus.CREATED);
    }

    @PutMapping("/{customerId}/updateProfile")
    public ResponseEntity<?> updateCustomerProfile(@RequestBody UpdateCustomerProfileRequest updateCustomerProfileRequest, @PathVariable String customerId){
        Customer updateCustomerProfileResponse   = customerServices.updateCustomerProfile(updateCustomerProfileRequest, customerId);
        return new ResponseEntity<>(updateCustomerProfileResponse, HttpStatus.CREATED);
    }




}
