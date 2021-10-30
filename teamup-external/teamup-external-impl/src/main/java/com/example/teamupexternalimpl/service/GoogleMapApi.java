package com.example.teamupexternalimpl.service;

import com.example.teamupexternalimpl.model.MapEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Path("/api")
@Component
@Slf4j
public class GoogleMapApi {

	@Value("${google_map_geoCoding_url}")
	private String urlGeocode;

	@Value("${google_map_geoDecoding_url}")
	private String urlGeoDecode;

	@Value("${google_map_api_key}")
	private String apiKey;

	@GET
	@Path("/code/{address}")
	@Produces(MediaType.APPLICATION_JSON)
	public MapEntity getGeocode(@PathParam("address") String address) {

		log.debug("Старт метода GetGeocode с параметром {}.", address);
		String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
		log.debug("Кодируем address. Результат кодирования: " + encodedAddress);
		String url = urlGeocode + encodedAddress + "&key=" + apiKey;
		log.debug("Формируем URL запроса: " + url);

		MapEntity geo = null;

		javax.ws.rs.client.Client client = ClientBuilder.newClient();
		log.debug("Создаем объект Client");

		Response response = client.target(url).request().get();
		log.debug("Получаем ответ от сервера Гугла");

		if (response.getStatus() != 200) {
			log.error("Error: " + response.getStatusInfo());
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		try {
			geo = response.readEntity(MapEntity.class);
			log.debug("Получаем объект GeoResult из Response");
		} catch (Exception e) {
			log.error("Ошибка получения entity из response: " + Arrays.toString(e.getStackTrace()));
		}
		return geo;
	}

	@GET
	@Path("/decode/{geo}")
	@Produces(MediaType.APPLICATION_JSON)
	public MapEntity getAddress(@PathParam("geo") String geo) {

		log.debug("Старт метода getAddress с параметром {}.", geo);

		MapEntity mapEntity = null;

		final String baseUrl = urlGeoDecode + geo + "&location_type=ROOFTOP&result_type=street_address&key=" + apiKey;
		Client client = Client.create();

		WebResource webResource = client.resource(baseUrl);
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			log.error("Ошибка в получении адреса в методе getAddress с параметром {}.", geo);
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapEntity = mapper.readValue(responseString(response), MapEntity.class);
		} catch (Exception e) {
			log.error("Ошибка в обработке адреса в методе getAddress с параметром {}.", geo);
			e.printStackTrace();
		}

		return mapEntity;
	}

	private String responseString(ClientResponse response) {
		InputStream stream = response.getEntityInputStream();
		StringBuilder textBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName(StandardCharsets.UTF_8.name())))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
			return textBuilder.toString();
		} catch (IOException e) {
			log.error("Ошибка в приведении ClientResponse к типу String в методе responseString.");
			return null;
		}
	}
}