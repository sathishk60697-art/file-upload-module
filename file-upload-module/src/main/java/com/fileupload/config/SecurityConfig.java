package com.fileupload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Swagger-kku vendiya paths-ai inge add pannirukken
            		.requestMatchers("/api/files/**", "/h2-console/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui/index.html").permitAll()
                .anyRequest().authenticated()
            );
        
        // H2 console use panreenga-na idhuvum add pannanum (frames allowed illa-na console work aagathu)
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        
        return http.build();
    }
}