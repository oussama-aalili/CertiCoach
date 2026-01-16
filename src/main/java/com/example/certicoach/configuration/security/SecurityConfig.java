package com.example.certicoach.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // Autorisatie
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> {
                    // Only users with the USER role can access /hello
                    authorize.requestMatchers("/vragen").hasRole("USER");

                    // Only users with the ADMIN role can access the rest
                    authorize.requestMatchers("/actuator/**", "/message/**").hasRole("ADMIN");

                    // Deny access to any request for users without credentials
                    authorize.anyRequest().authenticated();
                })
                .httpBasic(Customizer.withDefaults())
                .anonymous(AbstractHttpConfigurer::disable);  // Disables anonymous access (users without credentials)

        return http.build();
    }

    // Authenticatie
    @Bean
    public UserDetailsService userDetailsService(){

        UserDetails alex = User.builder()
                .username("alex")
                .password(passwordEncoder().encode("alex"))
                .roles("USER")
                .build();

        UserDetails sophie = User.builder()
                .username("sophie")
                .password(passwordEncoder().encode("sophie"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(alex,sophie);
    }
}