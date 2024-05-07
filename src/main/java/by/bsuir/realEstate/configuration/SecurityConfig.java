package by.bsuir.realEstate.configuration; //настройка доступа к url

import by.bsuir.realEstate.services.AccountDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig{
    private final AccountDetailsService accountDetailsService;

    private final JWTFilter jwtFilter;

    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(AccountDetailsService accountDetailsService, JWTFilter jwtFilter, CorsConfigurationSource corsConfigurationSource) {
        this.accountDetailsService = accountDetailsService;
        this.jwtFilter = jwtFilter;
        this.corsConfigurationSource = corsConfigurationSource;
    }
    private final String[] BLACK_LIST_GET = { "/api/auth"};
    private final String[] BLACK_LIST_POST = {"/api/auth/registrationForAdmin", "/api/types"};
    private final String[] BLACK_LIST_PUT = {"/api/types/{id}"};
    private final String[] BLACK_LIST_DELETE = {"/api/auth/{id}","/api/types/{id}"};
    private final String[] AUTHORIZED_PUT = { "/api/auth/change/{id}", "/api/apartments/{id}"};
    private final String[] AUTHORIZED_GET = {"/api/favorites"};
    private final String[] AUTHORIZED_DELETE = {"/api/apartments/{id}"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource))
                .securityMatcher("/api/**")
                .authorizeHttpRequests(req ->
                        req.requestMatchers(HttpMethod.POST, BLACK_LIST_POST)
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, BLACK_LIST_DELETE)
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, BLACK_LIST_GET)
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, BLACK_LIST_PUT)
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, AUTHORIZED_GET)
                                .authenticated()
                                .requestMatchers(HttpMethod.PUT, AUTHORIZED_PUT)
                                .authenticated()
                                .requestMatchers(HttpMethod.DELETE, AUTHORIZED_DELETE)
                                .permitAll()
                                .anyRequest()
                                .permitAll()
                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
