package ru.team.up.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.endpoint.NimbusAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.team.up.core.entity.User;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessHandler successHandler;
    private final UserDetailsService userDetailsService;
    private final FailureHandler failureHandler;

    @Autowired
    public SecurityConfig(SuccessHandler successHandler, @Qualifier("userDetailsImpl") UserDetailsService userDetailsService, FailureHandler failureHandler) {
        this.successHandler = successHandler;
        this.userDetailsService = userDetailsService;
        this.failureHandler = failureHandler;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService (userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/registration", "/login", "/oauth2reg", "/oauth2/authorization/google").anonymous()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/moderator").hasRole("MODERATOR")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").successHandler(successHandler)
                .loginProcessingUrl("/login").usernameParameter("auth_email").passwordParameter("auth_password").permitAll()
                .and()
                .oauth2Login().loginPage("/oauth2/authorization/google")
                .successHandler(successHandler)
                .failureHandler(failureHandler);

        http.logout()//URL выхода из системы безопасности Spring - только POST. Вы можете поддержать выход из системы без POST, изменив конфигурацию Java
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//выход из системы гет запрос на /logout
                .logoutSuccessUrl("/")//успешный выход из системы
                .and().csrf().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest>
//    accessTokenResponseClient() {
//
//        return new NimbusAuthorizationCodeTokenResponseClient();
//    }

    @Bean
    public PrincipalExtractor principalExtractor() {
        return map -> {
            return new User();
        };
    }

//    @Bean
//    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService(WebClient rest) {
//        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
//        return request -> {
//            OAuth2User user = delegate.loadUser(request);
//            if (!"github".equals(request.getClientRegistration().getRegistrationId())) {
//                return user;
//            }
//
//            OAuth2AuthorizedClient client = new OAuth2AuthorizedClient
//                    (request.getClientRegistration(), user.getName(), request.getAccessToken());
//            String url = user.getAttribute("organizations_url");
//            List<Map<String, Object>> orgs = rest
//                    .get().uri(url)
//                    .attributes(oauth2AuthorizedClient(client))
//                    .retrieve()
//                    .bodyToMono(List.class)
//                    .block();
//
//            if (orgs.stream().anyMatch(org -> "spring-projects".equals(org.get("login")))) {
//                return user;
//            }
//
//            throw new OAuth2AuthenticationException(new OAuth2Error("invalid_token", "Not in Spring Team", ""));
//        };
//    }
}
