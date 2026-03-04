package EmpresaPkalab.service;

import EmpresaPkalab.model.Usuario;
import EmpresaPkalab.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario login(String correo, String password) {
        // Buscamos al usuario por correo
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Correo no registrado"));

        // Verificamos si está activo (el switch que pediste)
        if (!usuario.getEstado()) {
            throw new RuntimeException("Tu cuenta está inactiva. Contacta al soporte.");
        }

        // Comparamos la clave escrita con la encriptada en base de datos
        if (passwordEncoder.matches(password, usuario.getPassword())) {
            return usuario;
        } else {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }
}