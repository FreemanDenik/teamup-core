package ru.team.up.auth.config;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import ru.team.up.core.entity.User;

import java.util.Map;

public class GooglePrincipalExtractor implements PrincipalExtractor{
    @Override
    public User extractPrincipal(Map<String, Object> map) {
        User user = new User();
        user.setName((String) map.get("name"));
        user.setEmail((String) map.get("email"));

        return user;
    }
}

