package com.maiphong.quizapplication.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.maiphong.quizapplication.services.TokenService;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    private final TokenService tokenService;

    public SecurityConfiguration(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200")); // http://shopee.vn,
                                                                                      // http://tiki.vn
        config.addAllowedHeader("*"); // X-Requested-With, Content-Type, Authorization, Origin, Accept
        config.addAllowedMethod("*"); // GET, POST, PUT, DELETE, PATCH, OPTIONS
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTFilter(tokenService), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/api/manager/quizzes/**").hasRole("Admin")
                        .requestMatchers("/api/manager/roles/**").hasRole("Admin")
                        .requestMatchers("api/manager/auth/**").permitAll()
                        .requestMatchers("/api/manager/users/**").hasRole("Admin")
                        .requestMatchers("/api/manager/questions/**").hasRole("Admin")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

}
