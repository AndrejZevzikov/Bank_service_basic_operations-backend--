package com.final_project.daily_operations.service.for_controller;

import com.final_project.daily_operations.exception.EmailDoesNotExistException;
import com.final_project.daily_operations.exception.EmptyFieldsException;
import com.final_project.daily_operations.exception.TakenUsernameException;
import com.final_project.daily_operations.exception.UUIDExpiredOrDoesNotExistException;
import com.final_project.daily_operations.helper.AccountNumberGenerator;
import com.final_project.daily_operations.helper.RandomGenerator;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.repostory.AuthorityRepository;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.repostory.CustomerRepository;
import com.final_project.daily_operations.service.for_message.EmailService;
import com.final_project.daily_operations.validation.CustomerServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.final_project.daily_operations.constants.Constants.*;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService implements UserDetailsService {

    private CustomerRepository customerRepository;
    private CustomerServiceValidation customerServiceValidation;
    private EmailService emailService;
    private PasswordEncoder passwordEncoder;
    private AuthorityRepository authorityRepository;
    private CurrencyRepository currencyRepository;
    private AccountNumberGenerator accountNumberGenerator;

    public Customer saveNewCustomer(Customer customer) throws TakenUsernameException, EmptyFieldsException {
        customerServiceValidation.isValidRegistrationInformation(customer);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setAuthority(authorityRepository.findByAuthority("CLIENT"));
        customer.setIsEnabled(Boolean.TRUE);
        List<Balance> balances = new ArrayList<>();
        balances.add(Balance.builder()
                .amount(100.0)
                .currency(currencyRepository.findById(1L).get())
                .accountNumber(accountNumberGenerator.generate())
                .customer(customer)
                .build());
        customer.setBalances(balances);
        log.info("Saving user by username: {} and email {}", customer.getUsername(), customer.getEmail());
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerByUsername(String username) {
        log.info("searching username: {}", username);
        return customerRepository.findByUsername(username).get();
    }

    public void sendPasswordRecoveryLink(String email) throws EmailDoesNotExistException { //TODO maybe facade
        customerServiceValidation.isEmailExists(email);
        Customer customer = customerRepository.findByEmail(email).get();
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        customer.setUuid(uuidString);
        customerRepository.save(customer);
        String recoveryLink = RECOVERY_LINK + uuidString;
        emailService.sendMessage(customer, recoveryLink, RECOVERY_LINK_SUBJECT);
        log.info(String.format("For user %s send recovery url %s", customer.getUsername(), recoveryLink));
    }

    public void sendNewPassword(String uuid) throws UUIDExpiredOrDoesNotExistException {
        customerServiceValidation.isUuidAvailable(uuid);
        String newPassword = RandomGenerator.getRandomString(10);
        Customer customer = customerRepository.findByUuid(uuid).get();
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
        emailService.sendMessage(customer, newPassword, NEW_PASSWORD_SUBJECT);
        log.info("For user: {} was send new password", customer.getUsername());

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUsername(username).get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(customer.getAuthority().getAuthority()));
        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), authorities);
    }
}


