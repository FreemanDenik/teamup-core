package ru.team.up.external.impl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
*  Сущность - ограничивающая рамка, которая может полностью содержать возвращаемый результат
* */

@Getter
@Setter
@NoArgsConstructor
public class Bounds {

    /*
    * северо-восток
    * */
    private Northeast northeast;

    /*
    * юго-запад
    * */
    private Southwest southwest;
}