package ru.team.up.input.service.impl;

import org.springframework.stereotype.Service;
import ru.team.up.input.service.Validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class EmailValidatorService implements Validator {
    @Override
    public boolean validate(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}
