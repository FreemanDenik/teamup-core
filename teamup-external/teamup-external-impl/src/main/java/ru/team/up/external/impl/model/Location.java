package ru.team.up.external.impl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
* Сущность - геокодированные значения широты и долготы
* */

@Getter
@Setter
@NoArgsConstructor
public class Location {

    /*
    * Широта
    * */
    private double lat;

    /*
    * Долгота
    * */
    private double lng;
}