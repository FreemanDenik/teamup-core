package com.example.teamupexternalimpl;

import com.example.teamupexternalimpl.model.map.MapEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Path("/api")
public class GoogleMapApi
{

	@GET
	@Path("/{geo}")
	@Produces(MediaType.APPLICATION_JSON)
	public MapEntity getAllEmployees(@PathParam("geo") String geo)
	{
		MapEntity mapEntity = null;

		final String baseUrl1 = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
		final String baseUrl2 = "&location_type=ROOFTOP&result_type=street_address&key=AIzaSyDlL0gkesL_aAoflRtsO_Y-XIJJgBFiZuw";
		final String baseUrl = baseUrl1 + geo + baseUrl2;
		Client client = Client.create();

		WebResource webResource = client.resource(baseUrl);
		ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON_TYPE).get(ClientResponse.class);

		if (response.getStatus() != 200){
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}

		ObjectMapper mapper = new ObjectMapper();
		try{
			mapEntity = mapper.readValue(responseString(response), MapEntity.class);
		}catch (Exception e){
			e.printStackTrace();
		}

		return  mapEntity;
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
			e.printStackTrace();
			return null;
		}
	}

}


