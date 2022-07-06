package ru.service;

public interface GeoService {

    //Метод для получения координат по адресу
    String getGeoCoordinates(String address);

    //Метод для получения адреса из координат
    String getAddress(String coordinates);

}
