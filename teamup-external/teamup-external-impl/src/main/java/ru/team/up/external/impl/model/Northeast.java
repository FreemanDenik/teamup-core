package ru.team.up.external.impl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Сущность - координаты северо-востока
 * */

@Getter
@Setter
@NoArgsConstructor
public class Northeast {

    /*
     * Широта
     * */
    public double lat;

    /*
     * Долгота
     * */
    public double lng;
}