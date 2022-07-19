package ru.team.up.auth.config;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;


@Component
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        if (oAuth2UserRequest.getClientRegistration().getRegistrationId().equals("custom")) {
            return new CustomOAuth2User(loadVkUser(oAuth2UserRequest));
        }
        return new CustomOAuth2User(super.loadUser(oAuth2UserRequest));

    }


    private OAuth2User loadVkUser(OAuth2UserRequest oAuth2UserRequest) {
        RestTemplate template = new RestTemplate();

        MultiValueMap<String, String> headers = new LinkedMultiValueMap();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", oAuth2UserRequest.getAccessToken().getTokenType().getValue() + " " + oAuth2UserRequest.getAccessToken().getTokenValue());
        HttpEntity<?> httpRequest = new HttpEntity(headers);
        String uri = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        String userNameAttributeName = oAuth2UserRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        uri = uri.replace("{user_id}", userNameAttributeName + "=" + oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));
        try {
            ResponseEntity<Object> entity = template.exchange(uri, HttpMethod.GET, httpRequest, Object.class);
            Map<String, Object> response = (Map) entity.getBody();
            ArrayList valueList = (ArrayList) response.get("response");
            Map<String, Object> userAttributes = (Map<String, Object>) valueList.get(0);
            userAttributes.put("name", oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));
            userAttributes.put("email", oAuth2UserRequest.getAdditionalParameters().get(userNameAttributeName));
            Set<GrantedAuthority> authorities = Collections.singleton(new OAuth2UserAuthority(userAttributes));
            return new DefaultOAuth2User(authorities, userAttributes, "name");

        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}