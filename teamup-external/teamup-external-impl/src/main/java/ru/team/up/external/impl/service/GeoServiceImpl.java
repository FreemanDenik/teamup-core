package ru.team.up.external.impl.service;

import ch.qos.logback.core.net.server.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.team.up.external.api.service.GeoService;
import ru.team.up.external.impl.model.Location;
import ru.team.up.external.impl.model.MapEntity;

import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Service
public class GeoServiceImpl implements GeoService {

    @Value("https://maps.googleapis.com/maps/api/geocode/json?address=")
    private String urlGeocode;

    @Value("https://maps.googleapis.com/maps/api/geocode/json?latlng=")
    private String urlGeoDecode;

    @Value("AIzaSyAHQIILvWIWOguqyXX9nCexRuHNz2y-gO8")
    private String apiKey;

    private Client client ;

    @Autowired
    public GeoServiceImpl(Client client){
        this.client = client;
    }

    @Override
    public String getGeoPosition(String address) {
        log.debug("Старт метода GetGeocode с параметром {}", address);
        Location location = getResponseFromGoogleMapApi(urlGeocode, address)
                .getMapResourceResults().get(0).geometry.location;
        return String.format("%f,%f", location.getLat(), location.getLng());
    }

    @Override
    public String getAddress(String code) {
        log.debug("Старт метода GetAddress с параметром {}", code);
        return getResponseFromGoogleMapApi(urlGeoDecode, code)
                .getMapResourceResults().get(0).formattedAddress;
    }

    public MapEntity getResponseFromGoogleMapApi(String url, String addressOrCode) {
        log.debug("Старт метода getResponseFromGoogleMapApi с параметром {}", addressOrCode);
        String encodedAddress = URLEncoder.encode(addressOrCode, StandardCharsets.UTF_8);
        log.debug("Кодируем параметр запроса. Результат кодирования: {}", encodedAddress);
        String urlResult = url + encodedAddress + "&key=" + apiKey;
        log.debug("Формируем URL запроса: {}", urlResult);
        MapEntity mapEntity = null;
//        Response response = client.target(urlResult).request().get();
//
//        log.debug("Получаем ответ от сервера Гугла {}", response);
//        if (response.getStatus() != 200) {
//            log.error("Error response : {}", response.getStatusInfo());
//            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//        }
//        try {
//            mapEntity = response.readEntity(MapEntity.class);
//            log.debug("Получаем объект MapEntity из Response");
//        } catch (Exception e) {
//            log.error("Ошибка получения entity из response: {}", Arrays.toString(e.getStackTrace()));
//        }
        return mapEntity;
    }
}
