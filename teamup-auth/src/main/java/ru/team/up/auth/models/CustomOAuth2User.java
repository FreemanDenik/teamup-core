package ru.team.up.auth.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ru.team.up.auth.enums.EnumProviders;

import java.util.Collection;
import java.util.Map;

/**
 * Класс-модель, которая заполняется краткими данными о пользователе регистрируемый через сторонний сервис
 */
@Getter
@Setter
public class CustomOAuth2User implements OAuth2User {

    private OAuth2User oAuth2User;
    private String firstName;
    private String lastName;
    private String birthday;
    private String city;
    private String email;

    public CustomOAuth2User(OAuth2User oAuth2User, EnumProviders enumProviders) {
        this.oAuth2User = oAuth2User;
        this.firstName = oAuth2User.getAttribute("first_name");
        this.lastName = oAuth2User.getAttribute("last_name");
        // json у каждого сервиса свой, подгоняем их под общую сущность
        switch (enumProviders) {
            case VKONTAKTE:
                this.birthday = oAuth2User.getAttribute("bdate");
                this.email = oAuth2User.getAttribute("email");
                this.city = oAuth2User.getAttribute("home_town");

                break;
            case YANDEX:
                this.birthday = oAuth2User.getAttribute("birthday");
                this.email = oAuth2User.getAttribute("default_email");
                break;
        }
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return firstName;
    }
}
