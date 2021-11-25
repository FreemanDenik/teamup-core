package ru.team.up.external.api.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.team.up.external.impl.service.GeoService;

//import javax.ws.rs.client.Client;

@Slf4j
@Service
@AllArgsConstructor
public class GeoServiceApiImpl implements GeoServiceApi {

    private final GeoService geoService;

    /*
     * Метод для получения георафических координат по адресу
     * Укажите адреса в соответствии с форматом, используемым национальной почтовой службой соответствующей страны.
     * Следует избегать дополнительных элементов адреса, таких как названия компаний и номера подразделений, апартаментов или этажей.
     * Элементы почтового адреса должны быть разделены пробелами (показаны здесь как URL-адреса с экранированием до %20):
     * address=24%20Sussex%20Drive%20Ottawa%20ON
     *
     * @param address Почтовый адрес или плюс код, который вы хотите геокодировать.
     * @return Сущность с данными о месте и статус запроса
     * */
    @Override
    public String getGeocodeApi(String address) {
        log.debug("Старт метода GetGeocode с параметром {}", address);
        return geoService.getGeocode(address);
    }

    /*
     * Метод для получения удобочитаемого адреса из географических координат
     * Важно! Между значениями широты и долготы не должно быть пробелов
     *
     * @param geo Значение широты / долготы. Пример: 40.714224,-73.961452
     * @return Сущность с данными о месте и статус запроса
     * */
    @Override
    public String getAddressApi(String code) {
        log.debug("Старт метода GetAddress с параметром {}", code);
        return geoService.getAddress(code);
    }
}