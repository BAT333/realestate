package com.example.realestate.config.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter filter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(http->http.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorization->
                        authorization.requestMatchers(HttpMethod.POST,"/auth").permitAll()
                                .requestMatchers(HttpMethod.POST,"/auth/*").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/propertie/{id}").hasRole("SUB")
                                .requestMatchers(HttpMethod.PUT,"/propertie/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.DELETE,"/propertie/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/propertie/{id}").hasRole("USER")
                                .requestMatchers(HttpMethod.GET,"/propertie").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST,"/proprietor/register").permitAll()
                                .requestMatchers(HttpMethod.GET,"/proprietor").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/proprietor/{id}").hasRole("SUB")
                                .requestMatchers(HttpMethod.PUT,"/proprietor/{id}").hasRole("SUB")
                                .requestMatchers(HttpMethod.DELETE,"/proprietor/{id}").hasRole("SUB")
                                .requestMatchers(HttpMethod.POST,"/contract/**").hasRole("SUB")
                                .requestMatchers(HttpMethod.GET,"/contract").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/contract/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE,"/contract/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET,"/search/**").permitAll()
                                .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }




    @Bean
    public AuthenticationManager manager (AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
