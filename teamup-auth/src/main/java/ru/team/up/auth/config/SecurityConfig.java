package ru.team.up.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private SuccessHandler successHandler;
    private UserDetailsService userDetailsService;

    public SecurityConfig(SuccessHandler successHandler,UserDetailsService userDetailsService){
        this.successHandler = successHandler;
        this.userDetailsService= userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService (userDetailsService);
        auth.inMemoryAuthentication()
                .withUser("User")
                .password("$2a$12$YiROvFRAbeIWWz5XHJAfAedV5hVfb4NFKZs5Atw0yrB3yldjRTVmK")
                .authorities("ROLE_USER")
                .and()
                .withUser("Admin")
                .password("$2a$12$KwmQ4mBkrb70yebG.yjvKu2Cj5nf6iemWOvo6vOtNExZgMGgpdJte")
                .authorities("ROLE_ADMIN")
                .and ()
                .withUser ("Moderator")
                .password ("$2a$12$VeRlXvgPPjIzNayOwopCmOu01sjPQWCidCRa5wGEDiQWmUuElQDYG")
                .authorities ("ROLE_MODERATOR");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/registration","/login", "/oauth2/authorization/google").anonymous ()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user").hasRole("USER")
                .antMatchers("/moderator").hasRole("MODERATOR")
                .anyRequest().authenticated()
                .and().formLogin()
                .successHandler(successHandler)
                .and ().oauth2Login()
                .successHandler (new SuccessHandler());

        http.logout ()//URL выхода из системы безопасности Spring - только POST. Вы можете поддержать выход из системы без POST, изменив конфигурацию Java
                .logoutRequestMatcher (new AntPathRequestMatcher ("/logout"))//выход из системы гет запрос на /logout
                .logoutSuccessUrl ("/")//успешный выход из системы
                .and().csrf().disable();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
