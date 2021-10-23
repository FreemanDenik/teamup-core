package ru.team.up.input;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.input.service.Validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TeamupInputApplicationTests {

    private final String[] validEmailIds = new String[]{"journaldev@yahoo.com", "journaldev-100@yahoo.com",
            "journaldev.100@yahoo.com", "journaldev111@journaldev.com", "journaldev-100@journaldev.net",
            "journaldev.100@journaldev.com.au", "journaldev@1.com", "journaldev@gmail.com.com",
            "journaldev+100@gmail.com", "journaldev-100@yahoo-test.com", "journaldev_100@yahoo-test.ABC.CoM"};


    private final String[] invalidEmailIds = new String[]{"journaldev", "journaldev@.com.my",
            "journaldev123@.com", "journaldev123@.com.com",
            "journaldev()*@gmail.com", "journaldev@%*.com",
            "journaldev@journaldev@gmail.com"};

    private final String[] validNumbers = new String[]{
            "+79261234567",
            "89261234567",
            "79261234567",
            "+7 926 123 45 67",
            "8(926)123-45-67",
            "123-45-67",
            "9261234567",
            "79261234567",
            "(495)1234567",
            "(495) 123 45 67",
            "89261234567",
            "(495) 123 45 67",
            "8 927 1234 234",
            "8 927 12 12 888",
            "8 927 12 555 12"
    };
    private final String[] invalidNumbers = new String[]{"+89033271243","+3456002938","qwerty"};

    @Test
    void validNumbers(@Autowired Validator phoneNumberValidation) {
        for (String temp: validNumbers) {
            assertTrue(phoneNumberValidation.validateNumber(temp));
        }
        for (String temp: invalidNumbers){
            assertFalse(phoneNumberValidation.validateNumber(temp));
        }
    }

    @Test
    void validEmail(@Autowired Validator emailValidation) {
        for (String temp : validEmailIds) {
            assertTrue(emailValidation.validateEmail(temp));
        }
        for (String temp : invalidEmailIds) {
            assertFalse(emailValidation.validateEmail(temp));
        }
    }

}
