package ru.team.up.external.api.controller;


/*
 * Контроллер отвечающий за взаимодействие с Google api
 * */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.team.up.external.api.service.GeoServiceApi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
@Component
@Slf4j
public class GoogleMapApiApi {

    private GeoServiceApi geoService;

    public GoogleMapApiApi() {
    }

    @Autowired
    public GoogleMapApiApi(GeoServiceApi geoService) {
        this.geoService = geoService;
    }

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
    @GET
    @Path("/code/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGeoCode(@PathParam("address") String address) {
        return geoService.getGeocodeApi(address);
    }

    /*
    * Метод для получения удобочитаемого адреса из географических координат
    * Важно! Между значениями широты и долготы не должно быть пробелов
    *
    * @param geo Значение широты / долготы. Пример: 40.714224,-73.961452
    * @return Сущность с данными о месте и статус запроса
    * */
    @GET
    @Path("/decode/{geo}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAddress(@PathParam("geo") String geo) {
        return geoService.getAddressApi(geo);
    }
}