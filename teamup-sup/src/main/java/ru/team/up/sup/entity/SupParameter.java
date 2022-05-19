package ru.team.up.sup.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SupParameter<T> {

    private String name;
    private T value;

    public String getValueType() {
        switch (value.getClass().getSimpleName()) {
            case "Boolean":
                return "BOOLEAN";
            case "String":
                return "STRING";
            case "Integer":
                return "INTEGER";
            case "Double":
                return "DOUBLE";
            default:
                return null;
        }
    }
}
