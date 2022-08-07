package com.final_project.daily_operations.validation;

import com.final_project.daily_operations.exception.EmailDoesNotExistException;
import com.final_project.daily_operations.exception.EmptyFieldsException;
import com.final_project.daily_operations.exception.TakenUsernameException;
import com.final_project.daily_operations.exception.UUIDExpiredOrDoesNotExistException;
import com.final_project.daily_operations.model.Customer;
import com.final_project.daily_operations.repostory.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class CustomerServiceValidation {

    public static final String USERNAME_ALREADY_TAKEN = "Username : %s already taken";
    public static final String REGISTRATION_FORM_HAS_SOME_EMPTY_FIELDS = "Registration form has some empty fields";
    public static final String USER_WITH_GIVEN_EMAIL_DOES_NOT_EXIST = "User with email: %s does not exist";
    public static final String UUID_DOES_NOT_EXISTS = "uuid: %s does not exists";
    private final CustomerRepository customerRepository;

    public void isValidRegistrationInformation(final Customer customer) throws TakenUsernameException, EmptyFieldsException {
        if (customerRepository.findByUsername(customer.getUsername()).isPresent()) {
            throw new TakenUsernameException(String.format(USERNAME_ALREADY_TAKEN, customer.getUsername()));
        }
        if (customer.getUsername() == null || customer.getEmail() == null || customer.getFirstName() == null ||
                customer.getLastName() == null || customer.getIdentificationNumber() == null || customer.getPassword() == null) {
            throw new EmptyFieldsException(REGISTRATION_FORM_HAS_SOME_EMPTY_FIELDS);
        }
    }

    public Customer isEmailExists(final String email) throws EmailDoesNotExistException {
        return customerRepository.findByEmail(email).orElseThrow(
                () -> new EmailDoesNotExistException(String.format(USER_WITH_GIVEN_EMAIL_DOES_NOT_EXIST, email)));
    }

    public void isUuidAvailable(final String uuid) throws UUIDExpiredOrDoesNotExistException {
        if (customerRepository.findByUuid(uuid).isEmpty()) {
            throw new UUIDExpiredOrDoesNotExistException(String.format(UUID_DOES_NOT_EXISTS, uuid));
        }
    }
}