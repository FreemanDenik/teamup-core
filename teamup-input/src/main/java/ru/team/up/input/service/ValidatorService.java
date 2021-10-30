package ru.team.up.input.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired()))
public class ValidatorService {

    private final Validator emailValidator;
    private final Validator phoneNumberValidator;

    public boolean validateEmail(String email) {
        return emailValidator.validate(email);
    }

    public boolean validateNumber(String number) {
        return phoneNumberValidator.validate(number);
    }

    public String uniformFormatNumber(String email) {
        return phoneNumberValidator.uniformFormat(email);
    }

    public String uniformFormatEmail(String number) {
        return phoneNumberValidator.uniformFormat(number);
    }
}
