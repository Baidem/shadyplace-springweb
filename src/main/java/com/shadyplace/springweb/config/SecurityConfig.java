package com.shadyplace.springweb.config;

import com.shadyplace.springweb.services.SpringAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SpringAuthService springAuthService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests(
                (request) ->
                        request
                                .requestMatchers("/login", "/").permitAll()
                                .requestMatchers("/admin").hasAuthority("ADMIN")
                                .requestMatchers("/bookings").hasAuthority("USER")
                                .anyRequest().authenticated()
        ).formLogin((login) ->
                login.loginPage("/login").defaultSuccessUrl("/").permitAll())
                .logout((logout) -> logout.logoutUrl("/logout"));

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(springAuthService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

}
