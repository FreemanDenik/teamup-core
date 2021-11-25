package ru.team.up.external.api.service;



/*
* Интерфейс для получения:
*  1. Георафических координат по адресу
*  2. Удобочитаемого адреса из географических координат
* */

public interface GeoServiceApi<S> {

    /*
    * Метод для получения георафических координат по адресу
    * */
    String getGeocodeApi(String address);

    /*
    * Метод для получения удобочитаемого адреса из географических координат
    * */
    String getAddressApi(String code);


}
