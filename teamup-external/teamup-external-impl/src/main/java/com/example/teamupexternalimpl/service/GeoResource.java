package com.example.teamupexternalimpl.service;
import com.example.teamupexternalimpl.model.GeoResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;


@Component
@Slf4j
@Path("/api/geocode")
public class GeoResource {

    @Value("${google_map_geocoding_url}")
    private String url;

    @Value("${google_map_api_key}")
    private String apiKey;

    @GET
    @Path("/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public GeoResult getGeocode(@PathParam("address") String address) {

        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        log.debug("Кодируем address. Результат кодирования: " + encodedAddress);
        String url = this.url + encodedAddress + "&key=" + this.apiKey;
        log.debug("Формируем URL запроса: " + url);

        GeoResult geo = null;

        Client client = ClientBuilder.newClient();
        log.debug("Создаем объект Client");

        Response response = client.target(url).request().get();
        log.debug("Получаем ответ от сервера Гугла");

        if (response.getStatus() != 200) {
            log.error("Error: " + response.getStatusInfo());
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
        }

        try {
            geo = response.readEntity(GeoResult.class);
            log.debug("Получаем объект GeoResult из Response");
        } catch (Exception e) {
            log.error("Ошибка получения entity из response: " + Arrays.toString(e.getStackTrace()));
        }
        return geo;
    }
}
