package ru.team.up.input.serviceTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.input.service.ValidatorService;
import ru.team.up.input.service.impl.EmailValidatorService;
import ru.team.up.input.service.impl.PhoneNumberValidatorService;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ValidatorsTest {

    private final ValidatorService validator =
            new ValidatorService(new EmailValidatorService(), new PhoneNumberValidatorService());

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
    private final String[] validFormatNumbers = new String[]{
            "+7(926)123-45-67",
            "+7(926)123-45-67",
            "+7(926)123-45-67",
            "+7(926)123-45-67",
            "+7(926)123-45-67",
            "+7(495)123-45-67",
            "+7(926)123-45-67",
            "+7(926)123-45-67",
            "+7(495)123-45-67",
            "+7(495)123-45-67",
            "+7(926)123-45-67",
            "+7(495)123-45-67",
            "+7(927)123-42-34",
            "+7(927)121-28-88",
            "+7(927)125-55-12"
    };
    private final String[] invalidNumbers = new String[]{"+89033271243", "+3456002938", "qwerty"};

    @Test
    public void validNumbers() {
        for (String temp : validNumbers) {
            assertTrue(validator.validateNumber(temp));
        }
        for (String temp : invalidNumbers) {
            assertFalse(validator.validateNumber(temp));
        }
    }

    @Test
    public void validEmail() {
        for (String temp : validEmailIds) {
            assertTrue(validator.validateEmail(temp));
        }
        for (String temp : invalidEmailIds) {
            assertFalse(validator.validateEmail(temp));
        }
    }

    @Test
    public void formatNumbers() {
        IntStream.range(0, validNumbers.length)
                .forEach(i -> assertEquals(validFormatNumbers[i], validator.uniformFormatNumber(validNumbers[i])));

    }
}
