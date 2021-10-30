package com.example.teamupexternalimpl.config;

import com.example.teamupexternalimpl.service.GoogleMapApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
//        register(GeoResource.class);
        register(GoogleMapApi.class);
    }
}
