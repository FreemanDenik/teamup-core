package ru.team.up.external.impl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.team.up.external.impl.model.MapEntity;
import ru.team.up.external.impl.service.GeoService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/external")
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

    @GET
    @Path("/code/{address}")
    @Produces(MediaType.APPLICATION_JSON)
    public MapEntity getGeoCode(@PathParam("address") String address) {
        return geoService.getGeocode(address);
    }

    @GET
    @Path("/decode/{geo}")
    @Produces(MediaType.APPLICATION_JSON)
    public MapEntity getAddress(@PathParam("geo") String geo) {
        return geoService.getAddress(geo);
    }
}