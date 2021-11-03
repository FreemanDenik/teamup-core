package ru.team.up.external.impl.config;

import ru.team.up.external.impl.controller.GoogleMapApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(GoogleMapApi.class);
    }
}
