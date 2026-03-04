package EmpresaPkalab.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Importante añadir esto
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    var opt = new CorsConfiguration();
                    opt.setAllowedOrigins(List.of("*"));
                    opt.setAllowedMethods(List.of("GET", "POST", "PATCH", "PUT", "DELETE"));
                    opt.setAllowedHeaders(List.of("*"));
                    return opt;
                }))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // 1. ÚNICA PUERTA PÚBLICA: El Login
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2. EL CRUD DE USUARIOS: Solo para quien tenga autoridad "ADMIN"
                        // Esto incluye: Registrar, Listar, Buscar por DNI, Editar por DNI y Checkbox
                        .requestMatchers("/api/usuarios/**").hasAuthority("ADMIN")
                        //REQUERIMIENTO IMPORTAR EXCEL
                        .requestMatchers("/api/requerimientos/**").hasAuthority("ADMIN")
                        // 3. CUALQUIER OTRA RUTA: Pide estar autenticado
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}