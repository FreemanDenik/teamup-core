package ru.team.up.external.api.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import ru.team.up.external.api.controller.GoogleMapApiApi;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(GoogleMapApiApi.class);
    }

    @Bean
    Client getClient(){
        return ClientBuilder.newClient();
    }
}
