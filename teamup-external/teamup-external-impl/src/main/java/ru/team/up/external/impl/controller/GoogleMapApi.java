package ru.team.up.external.impl.controller;

import ru.team.up.external.impl.model.MapEntity;
import ru.team.up.external.impl.service.GeoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/*
* Контроллер отвечающий за взаимодействие с Google api
* */

@Path("/api")
@Component
@Slf4j
public class GoogleMapApi {

    private GeoService geoService;

    public GoogleMapApi() {
    }

    @Autowired
    public GoogleMapApi(GeoService geoService) {
        this.geoService = geoService;
    }

    /*
    * Метод для получения георафических координат по адресу
    *
    * @param address Почтовый адрес или плюс код, который вы хотите геокодировать.
    * Укажите адреса в соответствии с форматом, используемым национальной почтовой службой соответствующей страны.
    * Следует избегать дополнительных элементов адреса, таких как названия компаний и номера подразделений, апартаментов или этажей.
    * Элементы почтового адреса должны быть разделены пробелами (показаны здесь как URL-адреса с экранированием до %20):
    * address=24%20Sussex%20Drive%20Ottawa%20ON
    *
    * @return Сущность с данными о месте и статус запроса
    * */
    @GET
    @Path("/code/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public MapEntity getGeoCode(@PathParam("address") String address) {
        return geoService.getGeocode(address);
    }

    /*
    * Метод для получения удобочитаемого адреса из географических координат
    *
    * @param geo Значение широты / долготы. Пример: 40.714224,-73.961452
    * Важно! Между значениями широты и долготы не должно быть пробелов
     *
    * @return Сущность с данными о месте и статус запроса
    * */
    @GET
    @Path("/decode/{geo}")
    @Produces(MediaType.APPLICATION_JSON)
    public MapEntity getAddress(@PathParam("geo") String geo) {
        return geoService.getAddress(geo);
    }
}