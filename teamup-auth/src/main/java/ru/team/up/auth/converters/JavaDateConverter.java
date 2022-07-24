package ru.team.up.auth.converters;

import ru.team.up.auth.exception.DateConvertException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static org.apache.commons.lang3.time.DateUtils.parseDateStrictly;

public class JavaDateConverter {
    private static final String[] formats = {
            "dd.MM.yyyy",
            "dd-MM-yyyy",
            "dd/MM/yyyy",
            "yyyy.MM.dd",
            "yyyy-MM-dd",
            "yyyy/MM/dd",
    };

    public static LocalDate parserToLocalDate(String date) throws DateConvertException {
        try {
            Date parseDate = parseDateStrictly(date, formats);
            DateFormat finalFormat = new SimpleDateFormat("yyyy-MM-dd");
            return LocalDate.parse(finalFormat.format(parseDate));
        } catch (ParseException e) {
            throw new DateConvertException("Ошибка при парсинге даты");
        }
    }
}
