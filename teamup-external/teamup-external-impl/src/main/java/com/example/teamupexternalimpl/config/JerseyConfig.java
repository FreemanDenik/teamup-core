package com.example.teamupexternalimpl.config;


import com.example.teamupexternalimpl.GoogleMapApi;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig 
{
    public JerseyConfig() 
    {
        register(GoogleMapApi.class);
    }
}