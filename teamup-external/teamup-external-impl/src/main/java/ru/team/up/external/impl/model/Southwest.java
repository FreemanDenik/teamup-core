package ru.team.up.external.impl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
 * Сущность - координаты юго-запада
 * */

@Getter
@Setter
@NoArgsConstructor
public class Southwest {

    /*
     * Широта
     * */
    public double lat;

    /*
     * Долгота
     * */
    public double lng;
}