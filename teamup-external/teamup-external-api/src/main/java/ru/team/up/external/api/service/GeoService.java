package ru.team.up.external.api.service;



/*
* Интерфейс для получения:
*  1. Георафических координат по адресу
*  2. Удобочитаемого адреса из географических координат
* */

public interface GeoService {

    /*
    * Метод для получения георафических координат по адресу
    * */
    String getGeoPosition(String address);

    /*
    * Метод для получения удобочитаемого адреса из географических координат
    * */
    String getAddress(String code);


}
