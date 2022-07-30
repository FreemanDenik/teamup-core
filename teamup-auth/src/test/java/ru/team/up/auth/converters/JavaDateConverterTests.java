package ru.team.up.auth.converters;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.util.Assert;

import java.time.LocalDate;

public class JavaDateConverterTests {
    @ParameterizedTest
    @ValueSource(strings = {
            "2000.01.30",
            "2000-01-30",
            "2000/01/30",
            "2000-1-30",
            "2000/1/30",
            "30.1.2000",
            "30-1-2000",
            "30/1/2000",
            "30.01.2000",
            "30-01-2000",
            "30/01/2000" })
    void JavaDateConverterTest(String date){

       LocalDate resultDate = JavaDateConverter.parserToLocalDate(date);
       Assert.isTrue(resultDate.toString().equals("2000-01-30"), "Не верный формат данных " + date);
    }
}
