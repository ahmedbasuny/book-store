package com.book.store.system.order.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(CsrfConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/actuator/**", "/v3/api-docs/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated());
        http.oauth2ResourceServer(
                oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(new GrantedAuthoritiesConverter());
        return jwtConverter;
    }

    //    @Bean
    //    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //        http.authorizeHttpRequests(c -> c.requestMatchers("/actuator/**", "/v3/api-docs/**")
    //                        .permitAll()
    //                        .anyRequest()
    //                        .authenticated())
    //                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //                .cors(CorsConfigurer::disable)
    //                .csrf(CsrfConfigurer::disable)
    //                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
    //        return http.build();
    //    }
}
