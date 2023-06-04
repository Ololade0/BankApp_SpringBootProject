package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.*;
import BankApp.App.Bank.dto.response.CustomerLoginResponse;
import BankApp.App.Bank.exception.CustomerCannotBeFound;
import BankApp.App.Bank.model.Account;
import BankApp.App.Bank.model.Customer;
import BankApp.App.Bank.model.Role;
import BankApp.App.Bank.model.enums.GenderType;
import BankApp.App.Bank.model.enums.RoleType;
import BankApp.App.Bank.repository.CustomerRepository;
import BankApp.App.Bank.repository.RoleRepository;

import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

;




@Service
@RequiredArgsConstructor
public class CustomerServicesImpl implements CustomerServices, UserDetailsService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    private final EmailService emailService;
    private final AccountService accountService;


    private final BCryptPasswordEncoder bCryptPasswordEncoder;




    @Override
    public Customer saveNewCustomer(CustomerRegisterRequest customerRegister)  {
        Customer customer = Customer.builder()
                .customerAge(customerRegister.getCustomerAge())
                .customerEmail(customerRegister.getCustomerEmail())
                .customerName(customerRegister.getCustomerName())
                .password(bCryptPasswordEncoder.encode(customerRegister.getCustomerPassword()))
                .roleHashSet(new HashSet<>())
                .gender(customerRegister.getGenderType())
                .build();

           Role userRole = new Role(RoleType.USER);
        userRole = roleRepository.save(userRole);
        customer.getRoleHashSet().add(userRole);
//        sendMail(customerRegister);
        return customerRepository.save(customer);

      }





    private void sendMail(CustomerRegisterRequest customerRegisterRequest) throws UnirestException {
        MailRequest mailRequest = MailRequest.builder()
                .sender(System.getenv("SENDER"))
                .receiver(customerRegisterRequest.getCustomerEmail())
                .subject("You are welcome")
                .body("Hello " + customerRegisterRequest.getCustomerName() + ". Thank you for banking with us")
                .build();
        emailService.sendSimpleMail(mailRequest);

    }



    @Override
    public long totalNumberOfCustomer()
    {

        return customerRepository.count();
    }

    @Override
    public Customer findCustomerById(String customerId) {
        return customerRepository.findCustomerByCustomerId(customerId);
    }

    @Override
    public Customer findCustomerByName(String customerName) {
        return customerRepository.findCustomerByCustomerName(customerName);
    }

    @Override
    public String deleteAll() {
        customerRepository.deleteAll();
        return "All customers successfully deleted";

    }

    @Override
    public Page<Customer> findAllCustomers(FindAllCustomerRequest findAllCustomerRequest) {
        Pageable pageable = PageRequest.of(findAllCustomerRequest.getPageNumber() - 1, findAllCustomerRequest.getNumberOfPages());
        return customerRepository.findAll(pageable);
    }

    @Override
    public String deleteCustomerById(String customerId) {
        Customer foundCustomer = customerRepository.findCustomerByCustomerId(customerId);
        if (foundCustomer != null) {
            customerRepository.deleteById(customerId);
            return "Customer Successfully deleted";
        }

        else throw new CustomerCannotBeFound("Customer with " + customerId + "cannot be found");
    }

    @Override
    public Customer updateCustomerProfile(UpdateCustomerProfileRequest updateCustomerProfileRequest, String customerId) {
        Customer foundCustomer = customerRepository.findCustomerByCustomerId(customerId);
        if (foundCustomer != null) {
            foundCustomer.setCustomerName(updateCustomerProfileRequest.getCustomerName());
            foundCustomer.setCustomerEmail(updateCustomerProfileRequest.getCustomerEmail());
            foundCustomer.setCustomerAge(updateCustomerProfileRequest.getCustomerAge());
            foundCustomer.setGender(GenderType.valueOf(updateCustomerProfileRequest.getGenderType()));
            return customerRepository.save(foundCustomer);
        }

        else throw new CustomerCannotBeFound("Customer with " + customerId + " cannot be found");
    }

    @Override
    public Customer findCustomerByEmail(String customerEmail) {
        Optional<Customer> foundCustomer = customerRepository.findCustomerByCustomerEmail(customerEmail);
        if (foundCustomer.isEmpty()) {
            throw new CustomerCannotBeFound("Customer cannot be found");
        }
        return foundCustomer.get();

    }

    @Override
    public CustomerLoginResponse loginUser(CustomerLoginRequestModel userLoginRequestModel) {
        var customer = customerRepository.findCustomerByCustomerEmail(userLoginRequestModel.getEmail());
        if(customer.isPresent() && customer.get().getPassword().equals(userLoginRequestModel.getPassword()));
        return buildSuccessfulLoginResponse(customer.get());
    }

    @Override
    public Account findAccountById(String customerId, String accountId) {
        Customer foundCustomer =  customerRepository.findCustomerByCustomerId(customerId);
        if(foundCustomer != null){
            return accountService.findAccountById(accountId);

        }
        throw new CustomerCannotBeFound("customer cannot be found");


    }



    private CustomerLoginResponse buildSuccessfulLoginResponse(Customer customer) {
        return CustomerLoginResponse.builder()
//                .code(201)
                .message("Login successful")
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findCustomerByCustomerEmail(username).orElse(null);
        if (customer != null){
            return new User(customer.getCustomerEmail(), customer.getPassword(), getAuthorities(customer.getRoleHashSet()));
        }
        throw new UsernameNotFoundException("User with email "+ username +" does not exist");
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleType().name())).collect(Collectors.toSet());
    }








    }





