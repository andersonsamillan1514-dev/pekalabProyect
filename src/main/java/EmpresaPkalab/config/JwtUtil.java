package EmpresaPkalab.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    // IMPORTANTE: En un proyecto real, esta llave no debería generarse al azar cada vez que reinicias
    // porque todos los tokens anteriores dejarían de servir. Por ahora para pruebas está bien.
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationTime = 86400000; // 24 horas

    // 1. Generar el Token (Ya lo tienes)
    public String generarToken(String correo, String rol) {
        return Jwts.builder()
                .setSubject(correo)
                .claim("rol", rol)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }

    // 2. EXTRAER EL CORREO (Para saber quién es el que envía el token)
    public String extraerCorreo(String token) {
        return extraerClaims(token).getSubject();
    }

    // 3. EXTRAER EL ROL (Para saber si es ADMIN o MOTORIZADO)
    public String extraerRol(String token) {
        return extraerClaims(token).get("rol", String.class);
    }

    // 4. VALIDAR EL TOKEN (Verificar que no haya expirado y que la firma sea real)
    public boolean validarToken(String token) {
        try {
            return !extraerClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // Método privado auxiliar para leer el contenido del token
    private Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}