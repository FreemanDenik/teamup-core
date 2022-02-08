package ru.team.up.external.impl;

import org.glassfish.jersey.client.JerseyInvocation;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.team.up.external.impl.model.MapEntity;
import ru.team.up.external.impl.service.GeoServiceImpl;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamupExternalImpApplicationTests {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private Client client;

    @InjectMocks
    private GeoServiceImpl geoService;

    Response mockResponse = Mockito.mock(Response.class);
    final JerseyInvocation.Builder mockBuilder = Mockito.mock(JerseyInvocation.Builder.class);
    final JerseyWebTarget mockJerseyWebTarget = Mockito.mock(JerseyWebTarget.class);

    String geocodeCorrectDataURL = "https://maps.googleapis.com/maps/api/geocode/json?address=1600%2BAmphitheatre%2BParkway%2C%2BMountain%2BView%2C%2BCA&key=AIzaSyAHQIILvWIWOguqyXX9nCexRuHNz2y-gO8";
    String geocodeIncorrectDataURL = "https://maps.googleapis.com/maps/api/geocode/json?address=1600%25bAmphitheatre%2BParkway%2C%2BMountain%2BView%2C%2BCA&key=AIzaSyAHQIILvWIWOguqyXX9nCexRuHNz2y-gO8";
    String addressCorrectDataURL = "https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224%2C-73.961452&key=AIzaSyAHQIILvWIWOguqyXX9nCexRuHNz2y-gO8";
    String addressIncorrectData = "https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224%2C+-73.961452%25&key=AIzaSyAHQIILvWIWOguqyXX9nCexRuHNz2y-gO8";

    @Test
    public void getGeocodeCorrectData() {
        MockClient(geocodeCorrectDataURL);
        Assert.assertEquals("OK", geoService.getGeoPosition("1600+Amphitheatre+Parkway,+Mountain+View,+CA"));
    }

    @Test
    public void getGeocodeIncorrectData() {
        MockClient(geocodeIncorrectDataURL);
        Assert.assertEquals(null, geoService.getGeoPosition("1600%bAmphitheatre+Parkway,+Mountain+View,+CA"));
    }

    @Test
    public void getAddressCorrectData() {
        MockClient(addressCorrectDataURL);
        Assert.assertEquals("OK", geoService.getAddress("40.714224,-73.961452"));
    }

    @Test
    public void getAddressIncorrectData() {
        MockClient(addressIncorrectData);
        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            geoService.getAddress("40.714224, -73.961452%");
        });
        assertNotNull(thrown.getMessage());
    }

    private void MockClient(String url) {

        if (url == geocodeCorrectDataURL || url == addressCorrectDataURL) {
            MapEntity map = new MapEntity();
            map.setStatus("OK");

            doReturn(map).when(mockResponse).readEntity(MapEntity.class);
            doReturn(200).when(mockResponse).getStatus();
        }

        if (url == geocodeIncorrectDataURL) {
            Mockito.when(mockResponse.readEntity(MapEntity.class)).thenReturn(null);
            doReturn(200).when(mockResponse).getStatus();
        }

        if (url == addressIncorrectData) {
            doReturn(400).when(mockResponse).getStatus();
        }

        Mockito.when(client.target(url)).thenReturn(mockJerseyWebTarget);
        Mockito.when(client.target(url).request()).thenReturn(mockBuilder);
        Mockito.when(client.target(url).request().get()).thenReturn(mockResponse);
    }
}
