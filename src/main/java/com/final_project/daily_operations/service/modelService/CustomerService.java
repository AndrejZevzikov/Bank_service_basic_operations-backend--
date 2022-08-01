package com.final_project.daily_operations.service.modelService;

import com.final_project.daily_operations.exception.*;
import com.final_project.daily_operations.helper.AccountNumberGenerator;
import com.final_project.daily_operations.helper.JwtDecoder;
import com.final_project.daily_operations.helper.RandomGenerator;
import com.final_project.daily_operations.model.Balance;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.repostory.AuthorityRepository;
import com.final_project.daily_operations.repostory.BalanceRepository;
import com.final_project.daily_operations.repostory.CurrencyRepository;
import com.final_project.daily_operations.repostory.CustomerRepository;
import com.final_project.daily_operations.service.for_message.EmailService;
import com.final_project.daily_operations.service.runtime.CurrencyConverter;
import com.final_project.daily_operations.validation.CustomerServiceValidation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.final_project.daily_operations.constants.Constants.*;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerService implements UserDetailsService {

    public static final String NO_SUCH_USERNAME_IN_DATABASE = "No such Username in Database";
    public static final LocalDate NOW = LocalDate.now();
    public static final String CUSTOMER_DO_NOT_EXIST = "Customer with id %d in doesn't exist";
    public static final String NO_OBJECT_IN_DATABASE_WITH_UUID = "No object in database with uui: ";
    public static final String ADMIN = "ADMIN";
    public static final String USER_WITH_GIVEN_USERNAME_DO_NOT_EXIST = "User with username: %s in database doesn't exist";
    private final CustomerRepository customerRepository;
    private final CustomerServiceValidation customerServiceValidation;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    private final CurrencyRepository currencyRepository;
    private final AccountNumberGenerator accountNumberGenerator;
    private final CurrencyConverter currencyConverter;
    private final BalanceRepository balanceRepository;
    private final JwtDecoder jwtDecoder;

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

    public Customer getCustomerByToken(final String token) throws NoSuchObjectInDatabaseException {
        return getCustomerByUsername(jwtDecoder.getUsername(token));
    }

    public Customer getCustomerByUsername(final String username) throws NoSuchObjectInDatabaseException {
        return customerRepository
                .findByUsername(username)
                .orElseThrow(() -> new NoSuchObjectInDatabaseException(NO_SUCH_USERNAME_IN_DATABASE));
    }

    public void sendPasswordRecoveryLink(final String email) throws EmailDoesNotExistException {
        Customer customer = customerServiceValidation.isEmailExists(email);
        final String uuid = UUID.randomUUID().toString();
        customer.setUuid(uuid);
        customerRepository.save(customer);
        final String recoveryLink = RECOVERY_LINK + uuid;
        emailService.sendMessage(customer, recoveryLink, RECOVERY_LINK_SUBJECT);
        log.info(String.format("For user %s send recovery url %s", customer.getUsername(), recoveryLink));
    }

    public void sendNewPassword(final String uuid) throws UUIDExpiredOrDoesNotExistException, NoSuchObjectInDatabaseException {
        customerServiceValidation.isUuidAvailable(uuid);
        String newPassword = RandomGenerator.getRandomString(10); //TODO properties failas
        Customer customer = customerRepository.findByUuid(uuid).orElseThrow(
                () -> new NoSuchObjectInDatabaseException(NO_OBJECT_IN_DATABASE_WITH_UUID + uuid));
        customer.setPassword(passwordEncoder.encode(newPassword));
        customerRepository.save(customer);
        emailService.sendMessage(customer, newPassword, NEW_PASSWORD_SUBJECT);
        log.info("For user: {} was send new password", customer.getUsername());
    }

    public Double getCustomerTotalAmountInEur(final String username) throws NoSuchObjectInDatabaseException {
        //TODO validations
        if (getCustomerByUsername(username).getAuthority().getAuthority().equals(ADMIN)) {
            return currencyConverter.convertBalanceByGivenDate(NOW, balanceRepository.findAll());
        }
        return currencyConverter.convertBalanceByGivenDate(NOW, getCustomerByUsername(username).getBalances());
    }

    public Double getCustomerTotalAmountInEurFromToken(final String token) throws NoSuchObjectInDatabaseException {
        return getCustomerTotalAmountInEur(jwtDecoder.getUsername(token));
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        Customer customer;
        try {
            customer = customerRepository.findByUsername(username).orElseThrow(
                    () -> new NoSuchObjectInDatabaseException(String.format(USER_WITH_GIVEN_USERNAME_DO_NOT_EXIST, username)));
        } catch (NoSuchObjectInDatabaseException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(customer.getAuthority().getAuthority()));
        return new org.springframework.security.core.userdetails.User(customer.getUsername(), customer.getPassword(), authorities);
    }

    public List<Customer> getFilteredListOfCustomers(final String phrase) {
        return getAllCustomers().stream()
                .filter(customer -> customer.getUsername().contains(phrase) ||
                        customer.getFirstName().contains(phrase) ||
                        customer.getLastName().contains(phrase) ||
                        customer.getEmail().contains(phrase))
                .collect(Collectors.toList());
    }

    public List<Customer> deleteCustomerById(final Long id) throws NoSuchObjectInDatabaseException {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new NoSuchObjectInDatabaseException(String.format(CUSTOMER_DO_NOT_EXIST, id)));
        if (!customer.getAuthority().getAuthority().equals(ADMIN)) {
            customerRepository.deleteById(id);
        }
        return getAllCustomers();
    }
}