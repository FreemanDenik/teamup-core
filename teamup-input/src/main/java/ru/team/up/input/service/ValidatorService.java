package ru.team.up.input.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.team.up.input.service.impl.EmailValidatorService;
import ru.team.up.input.service.impl.PhoneNumberValidatorService;

@Service
public class ValidatorService {

    private final Validator emailValidator;
    private final Validator phoneNumberValidator;

    @Autowired
    public ValidatorService(EmailValidatorService emailValidator, PhoneNumberValidatorService phoneNumberValidator) {
        this.emailValidator = emailValidator;
        this.phoneNumberValidator = phoneNumberValidator;
    }

    public boolean validateEmail(String email) {
        return emailValidator.validate(email);
    }

    public boolean validateNumber(String email) {
        return phoneNumberValidator.validate(email);
    }

    public String uniformFormatNumber(String email) {
        return phoneNumberValidator.uniformFormat(email);
    }
}
