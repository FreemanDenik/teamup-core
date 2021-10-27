package ru.team.up.input.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.team.up.input.service.Validator;

import javax.swing.text.MaskFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("phoneNumberValidator")
@Slf4j
public class PhoneNumberValidatorService implements Validator {
    private final Pattern pattern;
    private static final String RU_NUMBER_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public PhoneNumberValidatorService() {
        pattern = Pattern.compile(RU_NUMBER_PATTERN);
    }

    @Override
    public boolean validate(String hex) {
        Matcher matcher = pattern.matcher(hex);
        log.debug("Validate:{}", hex);
        if (matcher.matches()) {
            return true;
        }
        log.error("Number:{} is not valid", hex);
        return false;
    }

    @Override
    public String uniformFormat(String number) {
        validate(number);
        String codeMoscow = "495";
        number = number.replaceAll("[^0-9]", "");
        Character firstChar = number.charAt(0);
        if (number.length() < 8) {
            number = codeMoscow + number;
        }
        if (firstChar.equals('8')) {
            number = number.replaceFirst("[8]", "7");
        }
        if (!firstChar.equals('8') && !firstChar.equals('7')) {
            number = "7" + number;
        }
        try {
            MaskFormatter format = new MaskFormatter("+#(###)###-##-##");
            format.setValueContainsLiteralCharacters(false);
            number = format.valueToString(number);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return number;
    }
}
