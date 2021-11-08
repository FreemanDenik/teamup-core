package ru.team.up.external.impl.config;

import org.springframework.context.annotation.Bean;
import ru.team.up.external.impl.controller.GoogleMapApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(GoogleMapApi.class);
    }

    @Bean
    Client getClient(){
        return ClientBuilder.newClient();
    }
}
