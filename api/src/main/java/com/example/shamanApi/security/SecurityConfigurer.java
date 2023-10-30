package com.example.shamanApi.security;


import com.example.shamanApi.service.MyUserDetailsService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * klasa konfigurująca bezpieczenstwo i dostęp do poszczegolnych end-pointów.
 * */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer  {


    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }
    /**
     * funkcja tworząca Password Encoder.
     * @return password Encoder
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    /**
     * funkcja tworząca nowy autentykator, ustawiająca parametry dla UserSerwice, oraz PasswordEncoder
     * @return DaoAuthenticationProvide z ustawionymi właściwie parametrami.
     * */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    private RSAKey rsaKey;
    /**
     * funkcja tworząca nowy autentykator, ustawiająca parametry dla UserSerwice.
     *
     * @return DaoAuthenticationProvide z ustawionymi właściwie parametrami.
     * */
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    /**
     * funkcja określająca które strony wymagają autoryzacij a które są dostępne dla wszystkich
     *
     * @param       http - HttpSecurity używane przez API.
     * @return      http - HttpSecurity z zbudowanym poprawnie FilterChain.
     * */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .authorizeRequests( auth -> auth
                        .mvcMatchers("/user/**", "/restaurants/**","/discounts/**","/activity").permitAll()
                        .mvcMatchers("**/logout", "**/usercart/**", "/islogged").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .build();
    }

    /**
     * funckja wykorzystuje kucz RSA (wygenerowany w klasie {@link Jwks Jwks}), do stworzenia JWKSource
     *
     * @return JWKSource z nowo wygenerowanym kluczem RSA
     * */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    @Bean
    JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }




}

