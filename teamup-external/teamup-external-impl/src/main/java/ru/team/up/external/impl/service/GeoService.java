package ru.team.up.external.impl.service;

import org.springframework.stereotype.Service;
import ru.team.up.external.impl.model.MapEntity;

/*
 * Интерфейс для получения:
 *  1. Георафических координат по адресу
 *  2. Удобочитаемого адреса из географических координат
 * */
@Service
public interface GeoService {
    /*
     * Метод для получения георафических координат по адресу
     * */
    String getGeocode(String address);

    /*
     * Метод для получения удобочитаемого адреса из географических координат
     * */
    String getAddress(String code);

    MapEntity getResponseFromGoogleMapApi(String code, String address);
}
