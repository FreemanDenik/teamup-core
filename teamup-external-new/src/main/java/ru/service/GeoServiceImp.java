package ru.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.model.YandexRespond;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Service
public class GeoServiceImp implements GeoService{

    private final String keyAPI = "ed848935-bd87-4d0f-aa06-147c729ec985";

    private final String urlAPI = "https://geocode-maps.yandex.ru/1.x/?format=json&apikey=";

    @Override
    public String getGeoCoordinates(String address) {
        log.debug("Старт метода getGeoCoordinates с параметром {}", address);
        return getResp(address).getRespons().getGeoObjectCollection().getFeatureMember().get(0).getGeoObject().getPoint().getPos();
    }

    @Override
    public String getAddress(String coordinates) {
        log.debug("Старт метода GetAddress с параметром {}", coordinates);
        return getResp(coordinates).getRespons().getGeoObjectCollection().getFeatureMember().get(0).getGeoObject().
                getMetaDataProperty2().getGeocoderMetaData().getAddress().getFormatted();
    }

    private YandexRespond getResp(String address) {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String urlResult = urlAPI + keyAPI + "&geocode=" + encodedAddress;
        YandexRespond yandexRespond = new YandexRespond();
        Client client = ClientBuilder.newClient();
        Response response = client.target(urlResult).request().get();
        log.debug("Получаем ответ от сервера Яндекса {}", response);
        if (response.getStatus() != 200) {
            log.error("Error response : {}", response.getStatusInfo());
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }
        try {
            yandexRespond = response.readEntity(YandexRespond.class);
            log.debug("Получаем объект YandexRespond из Response");
        } catch (Exception e) {
            log.error("Ошибка получения entity из response: {}", Arrays.toString(e.getStackTrace()));
        }
        return yandexRespond;
    }

//    public void delimeter(String str) {
//        String lat, lng;
//        String[] words = str.split(" ");
//        lat = words[0];
//        System.out.println("Latitude: " + lat);
//        lng = words[1];
//        System.out.println("Longitude: " + lng);
//    }

}
