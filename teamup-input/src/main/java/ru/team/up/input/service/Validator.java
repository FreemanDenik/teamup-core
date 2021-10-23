package ru.team.up.input.service;

public interface Validator {
    boolean validateEmail(String email);
    boolean validateNumber(String number);
}
