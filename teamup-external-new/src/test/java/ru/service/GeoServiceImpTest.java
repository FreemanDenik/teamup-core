package ru.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GeoServiceImpTest {

    private final String correctAddress = "Москва+Льва+Толстого+16";

    private final String incorrectAddress = "Москва";

    private final String correctGeoCoordinates = "37.587093 55.733974";

    private final String incorrectGeoCoordinates = "37.587093 56.733765";

    private final String resGeoCoordinates = "37.587093 55.733974";

    private final String resAddress = "Россия, Москва, улица Льва Толстого, 16";

    private final GeoServiceImp geoServiceImp = new GeoServiceImp();

    @Test
    public void correctCoordinates() {
        Assert.assertEquals("Coordinates are not equal", resGeoCoordinates, geoServiceImp.getGeoCoordinates(correctAddress));
    }

    @Test
    public void correctAddress() {
        Assert.assertEquals("Addresses are not equal", resAddress, geoServiceImp.getAddress(correctGeoCoordinates));
    }

    @Test
    public void incorrectGeoCoordinates() {
        Assert.assertEquals("Coordinates are not equal", resGeoCoordinates, geoServiceImp.getGeoCoordinates(incorrectAddress));
    }

    @Test
    public void incorrectAddress() {
        Assert.assertEquals("Addresses are not equal", resAddress, geoServiceImp.getAddress(incorrectGeoCoordinates));
    }

}