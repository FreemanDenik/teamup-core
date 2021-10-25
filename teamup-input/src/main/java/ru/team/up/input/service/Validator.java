package ru.team.up.input.service;

public interface Validator {
    boolean validate(String value);
    String uniformFormat(String value);
}
