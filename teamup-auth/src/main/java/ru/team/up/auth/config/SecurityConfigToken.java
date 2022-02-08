package ru.team.up.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.team.up.auth.config.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigToken extends WebSecurityConfigurerAdapter {
    private final SuccessHandler successHandler;
    private final UserDetailsService userDetailsService;
    private JwtFilter jwtFilter;

    @Autowired
    public SecurityConfigToken(SuccessHandler successHandler, @Qualifier("userDetailsImpl") UserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.successHandler = successHandler;
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/registration", "/login", "/oauth2reg", "/oauth2/authorization/google", "/authority").permitAll()
                .antMatchers("/admin/**", "/private/**").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/moderator").hasRole("MODERATOR")
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout()//URL выхода из системы безопасности Spring - только POST. Вы можете поддержать выход из системы без POST, изменив конфигурацию Java
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//выход из системы гет запрос на /logout
                .logoutSuccessUrl("/")//успешный выход из системы
                .and().csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }
}

