package vn.lottefinance.pdms_core.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import vn.lottefinance.pdms_core.component.JwtAuthenticationFilter;
import vn.lottefinance.pdms_core.service.core.UserService;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserService userService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final PasswordEncoder passwordEncoder;



    private static final String[] SWAGGER_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(requests ->
                requests.requestMatchers("/api/auth/login", "/api/auth/register",
                        "/api/auth/init-role", "/msi/swagger/**", "/msi/swagger-ui/**", "/swagger-ui/**", "/v3/api-docs/**", "/api/barcode/**",
                        "/msi/v3/api-docs/**", "/swagger/**").permitAll()
                        .requestMatchers("/api/auth/token")
                        .hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/v1/api/user/**")
                        .hasAuthority("USER")
                        .requestMatchers("/v1/api/admin/**", "/v1/api/room/**", "/v1/api/switch/**")
                        .hasAuthority("ADMIN")
                        .requestMatchers( "/api/auth/authenticate").permitAll()
                        .requestMatchers("/api/user/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/role/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/permission/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/user/create").hasAuthority("ADMIN")
                        .requestMatchers("/api/sso/get-local-token").permitAll()
                        .anyRequest().authenticated()
        );
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors(cors -> cors.configurationSource(request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            return configuration;
        }));
        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
