package ru.team.up.external.impl.service;

import ru.team.up.external.impl.model.MapEntity;

/*
* Интерфейс для получения:
*  1. Георафических координат по адресу
*  2. Удобочитаемого адреса из географических координат
* */

public interface GeoService {

    /*
    * Метод для получения георафических координат по адресу
    * */
    MapEntity getGeocode (String address);

    /*
    * Метод для получения удобочитаемого адреса из географических координат
    * */
    MapEntity getAddress (String code);

}
