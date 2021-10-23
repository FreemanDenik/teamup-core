package ru.team.up.input.service.impl;

import org.springframework.stereotype.Service;
import ru.team.up.input.service.Validator;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.ParseException;
import javax.swing.text.MaskFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidatorImpl implements Validator {

    private final Pattern pattern;
    private static final String RU_NUMBER_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public ValidatorImpl() {
        pattern = Pattern.compile(RU_NUMBER_PATTERN);
    }


    @Override
    public boolean validateEmail(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean validateNumber(String hex) {
        Matcher matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    @Override
    public String uniformFormat(String number) {
        if (!validateEmail(number)){
            throw new IllegalArgumentException("Number is not valid");
        }
        String codeMoscow ="495";
        number = number.replaceAll("[^0-9]", "");
        Character firstChar = number.charAt(0);
        if (number.length()<8){
            number=codeMoscow+number;
        }
        if (firstChar.equals('8')) {
            number = number.replaceFirst("[8]", "7");
        }
        if (!firstChar.equals('8') && !firstChar.equals('7')) {
            number = "7"+number;
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
