package com.maroc_ouvrage.semployee.config;

import com.maroc_ouvrage.semployee.service.Imp.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enables @PreAuthorize
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // create session if needed
                )
                .securityContext(context -> context
                        .requireExplicitSave(false) // ✅ ensures SecurityContext is saved to the session
                )
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOriginPatterns(List.of("http://localhost:4200","http://localhost:7777"));
                            config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                            config.setAllowedHeaders(List.of("*"));
                            config.setAllowCredentials(true);// important if using cookies/session
                            return config;
                        })
                )
                .csrf(AbstractHttpConfigurer::disable)


                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/employees/**").hasAnyAuthority("ADMIN", "CREATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasAnyAuthority("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users/**").hasAnyAuthority("ADMIN", "CREATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyAuthority("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAuthority("ADMIN")
                        // Departments
                        .requestMatchers(HttpMethod.GET, "/api/departments/**").hasAnyAuthority("USER", "CREATOR", "EDITOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/departments/**").hasAnyAuthority("ADMIN", "CREATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/departments/**").hasAnyAuthority("ADMIN", "EDITOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/departments/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/conges/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                //.httpBasic(Customizer.withDefaults())

                .logout(LogoutConfigurer::permitAll)

                .exceptionHandling(ex -> ex
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                new AntPathRequestMatcher("/api/**")
                        )
                )

                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
