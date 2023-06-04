package BankApp.App.Bank.services;

import BankApp.App.Bank.dto.request.CustomerLoginRequestModel;
import BankApp.App.Bank.dto.request.CustomerRegisterRequest;
import BankApp.App.Bank.dto.request.FindAllCustomerRequest;
import BankApp.App.Bank.dto.request.UpdateCustomerProfileRequest;
import BankApp.App.Bank.dto.response.CustomerLoginResponse;
import BankApp.App.Bank.model.Customer;
import BankApp.App.Bank.model.enums.GenderType;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class CustomerServiceImplTest {
    @Autowired
    private CustomerServices customerService;

    Customer savedCustomer;


    @BeforeEach
    void setUp() throws UnirestException {

        CustomerRegisterRequest customerRegister = new CustomerRegisterRequest();
        customerRegister.setCustomerName("Adesuyi");
        customerRegister.setGenderType(GenderType.FEMALE);
        customerRegister.setCustomerEmail("Ololadedemilade@gmail.com");
        customerRegister.setCustomerAge("55");
        customerRegister.setCustomerPassword("12345");

     savedCustomer =  customerService.saveNewCustomer(customerRegister);
    }

    @AfterEach
    void tearDown() {

        customerService.deleteAll();
    }


    @Test
    public void customerCanBeRegister() throws UnirestException {
//        List<Account> accounts = List.of();
        CustomerRegisterRequest customerRegister = new CustomerRegisterRequest();
        customerRegister.setCustomerName("Adesuyi");
        customerRegister.setGenderType(GenderType.FEMALE);
        customerRegister.setCustomerEmail("Ololadedemilade@gmail.com");
        customerRegister.setCustomerAge("55");
        customerRegister.setCustomerPassword("12345");

       savedCustomer = customerService.saveNewCustomer(customerRegister);
        assertThat(savedCustomer.getCustomerId()).isNotNull();
        assertEquals(2, customerService.totalNumberOfCustomer());


    }

    @Test
    public void findCustomerById() {
        Customer foundCustomer = customerService.findCustomerById(savedCustomer.getCustomerId());
        assertThat(foundCustomer).isNotNull();
        assertThat(foundCustomer.getCustomerId()).isEqualTo(savedCustomer.getCustomerId());
    }


    @Test
    public void findAllCustomer() {
        FindAllCustomerRequest findAllCustomerRequest = FindAllCustomerRequest.builder()
                .numberOfPages(1)
                .pageNumber(2)
                .build();
        Page<Customer> customerPage=  customerService.findAllCustomers(findAllCustomerRequest);
        assertThat(customerPage).isNotNull();
        assertThat(customerPage.getTotalElements()).isGreaterThan(0);
    }

    @Test
    public void AllCustomerCanBeDeleted() {
        customerService.deleteAll();
        assertEquals(0, customerService.totalNumberOfCustomer());
    }

    @Test
    public void deleteCustomerById() {
        customerService.deleteCustomerById(savedCustomer.getCustomerId());
        assertEquals(0, customerService.totalNumberOfCustomer());

    }


    @Test
    public void CustomerProfileCanBeUpdated() {
        UpdateCustomerProfileRequest updateCustomerProfileRequest = UpdateCustomerProfileRequest.builder()
                .customerName("Ololade")
                .customerId(savedCustomer.getCustomerId())
                .genderType("FEMALE")
                .customerAge("28")
                .customerEmail("adesuyiololade@gmail.com")
                .build();
        Customer customer =   customerService.updateCustomerProfile(updateCustomerProfileRequest, updateCustomerProfileRequest.getCustomerId());
        assertEquals("Ololade", customer.getCustomerName());
        assertEquals("adesuyiololade@gmail.com", customer.getCustomerEmail());
        assertEquals("28", customer.getCustomerAge());
        assertThat(customer.getGender()).isNotNull();

    }

    @Test
    public void testThatfindCustomerBYEmail() {
        Customer foundCustomer = customerService.findCustomerByEmail(savedCustomer.getCustomerEmail());
        assertEquals("Ololadedemilade@gmail.com", foundCustomer.getCustomerEmail());

    }


    @Test
    public void customerCanLogin() {
        CustomerLoginRequestModel userLoginRequestModel = new CustomerLoginRequestModel();
        userLoginRequestModel.setPassword(savedCustomer.getPassword());
        userLoginRequestModel.setEmail(savedCustomer.getCustomerEmail());
        CustomerLoginResponse response =  customerService.loginUser(userLoginRequestModel);
        assertEquals("Login successful", response.getMessage());
//       assertEquals(200, response.getCode());

    }







}




