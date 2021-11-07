package ru.team.up.external.impl.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;
import ru.team.up.external.impl.controller.GoogleMapApi;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(GoogleMapApi.class);
    }
}
