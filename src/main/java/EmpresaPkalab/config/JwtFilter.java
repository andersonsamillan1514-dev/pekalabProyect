package EmpresaPkalab.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.validarToken(token)) {
                String correo = jwtUtil.extraerCorreo(token);

                // 1. EXTRAE EL ROL (Asegúrate que tu JwtUtil tenga este método)
                String rol = jwtUtil.extraerRol(token);

                // 2. CREA LA AUTORIDAD (ADMIN, MOTORIZADO, etc.)
                var authority = new org.springframework.security.core.authority.SimpleGrantedAuthority(rol);

                // 3. PÁSALE LA LISTA CON EL ROL A SPRING
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                        correo,
                        null,
                        java.util.List.of(authority) // <-- YA NO ESTÁ VACÍA
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}