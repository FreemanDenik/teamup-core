package ru.team.up.external.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.team.up.external.impl.service.GeoService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest

class TeamupExternalImplApplicationTests {

    @Autowired
    private GeoService geoService;

    @Test
    public void getGeocode_correctData(){
        Assert.assertEquals("OK", geoService.getGeocode("1600+Amphitheatre+Parkway,+Mountain+View,+CA").getStatus());
    }

    @Test
    public void getGeocode_incorrectData(){
        Assert.assertEquals(null, geoService.getGeocode("1600%bAmphitheatre+Parkway,+Mountain+View,+CA"));
    }

    @Test
    public void getAddress_correctData(){
        Assert.assertEquals("OK", geoService.getAddress("40.714224,-73.961452").getStatus());
    }

    @Test
    public void getAddress_incorrectData(){

        Throwable thrown = assertThrows(RuntimeException.class, () -> {
            geoService.getAddress("40.714224, -73.961452%");
        });
        assertNotNull(thrown.getMessage());
    }
}
