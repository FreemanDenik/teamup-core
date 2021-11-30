package ru.team.up.external.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.team.up.external.impl.service.GeoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeamupExternalImpApplicationTests {

    @Autowired
    private GeoServiceImpl geoService;

   @Test
    public void geoCodeCorrect(){
       String geoCreat = geoService.getGeocode("1600+Amphitheatre+Parkway,+Mountain+View,+CA");
       Assert.assertNotNull(geoCreat);
       Assert.assertEquals("37,422388,-122,084188", geoCreat);
   }
   @Test
    public void getAddressCorrect(){
       String getAddress = geoService.getAddress("37.422388,-122.084188");
       Assert.assertNotNull(getAddress);
       Assert.assertEquals("1600 Amphitheatre Pkwy St222, Mountain View, CA 94043, USA", getAddress);
   }
    @Test(expected = RuntimeException.class)
    public void geoCodeNotCorrect(){
        String geoCreat = geoService.getGeocode(" ");
        Assert.assertNotNull(geoCreat);
    }

    @Test(expected = RuntimeException.class)
    public void getAddressNotCorrect(){
        String getAddress = geoService.getAddress("1600+Amphitheatre+Pkwy+St222,+Mountain+View,+CA");
    }
}
