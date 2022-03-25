package ru.team.up.auth.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.team.up.auth.config.jwt.JwtProvider;
import ru.team.up.auth.service.impl.UserDetailsImpl;
import ru.team.up.core.entity.Account;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {
    private final UserDetailsImpl userService;
    private final JwtProvider jwtProvider;
    private static String token;
    @Autowired
    public SuccessHandler(JwtProvider jwtProvider, UserDetailsImpl userService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        if (authentication.toString().contains ("given_name")) {
            try {
                Account account = (Account) userService.loadUserByUsername(((DefaultOidcUser)authentication.getPrincipal()).getEmail());
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(
                                SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                                SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                                Collections.singleton(account.getRole())));
            } catch (UsernameNotFoundException e) {
                httpServletResponse.sendRedirect("/oauth2reg");
                return;
            }
        }
        Account account = (Account) userService.loadUserByUsername(((DefaultOidcUser)authentication.getPrincipal()).getEmail());
        token = jwtProvider.generateToken(account.getEmail());
        log.debug("Успешная авторизация id: {},  email: {},  JWT: {}", account.getId(), account.getEmail(), token);
        httpServletResponse.sendRedirect("/loginByGoogle");
    }

    public static String getToken() {
        return token;
    }
}
