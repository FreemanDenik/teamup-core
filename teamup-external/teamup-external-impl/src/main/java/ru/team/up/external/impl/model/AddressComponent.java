package ru.team.up.external.impl.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/*
* Сущность содержащая отдельные компоненты, применимые к этому адресу
* */

@Getter
@Setter
@NoArgsConstructor
public class AddressComponent {

    /*
    * Полное текстовое описание или имя компонента адреса, возвращаемое геокодером
    * */
    @JsonProperty("long_name")
    public String longName;

    /*
    *  Сокращенное текстовое имя компонента адреса, если таковое имеется
    * */
    @JsonProperty("short_name")
    public String shortName;

    /*
    * Массив, указывающий тип каждой части адреса
    * */
    public List<String> types;
}