package com.example.homestaymanager.security;

import com.example.homestaymanager.service.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST,
                                "/auth/login",
                                "/auth/customer/register"
                        ).permitAll()
                        .requestMatchers(HttpMethod.GET,
                                "/rooms/**",
                                "/roomTypes/**",
                                "/branches/**",
                                "/amenities/**",
                                "/categories/**",
                                "/roomPricings/**",
                                "/roomPhotos/**",
                                "/auth/**"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/roles/**",
                                "/rooms/**",
                                "/branches/**",
                                "/amenities/**",
                                "/categories/**",
                                "/roomTypes/**",
                                "/roomPricings/**",
                                "/roomPhotos/**",
                                "/uploads/**"
                        ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,
                                "/roles/**",
                                "/rooms/**",
                                "/branches/**",
                                "/amenities/**",
                                "/categories/**",
                                "/roomTypes/**",
                                "/roomPricings/**",
                                "/roomPhotos/**",
                                "/uploads/**"
                        ).hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/roles/**",
                                "/rooms/**",
                                "/branches/**",
                                "/amenities/**",
                                "/categories/**",
                                "/roomTypes/**",
                                "/roomPricings/**",
                                "/roomPhotos/**",
                                "/uploads/**"
                        ).hasAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("https://homestay-frontend-ruddy.vercel.app"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
