package EmpresaPkalab.service;

import EmpresaPkalab.model.Usuario;
import EmpresaPkalab.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(Usuario usuario) {
        // 1. Verificamos que el usuario traiga una contraseña
        if (usuario.getPassword() == null || usuario.getPassword().isEmpty()) {
            throw new RuntimeException("La contraseña no puede estar vacía");
        }

        // 2. ENCRIPTAMOS LO QUE VIENE EN EL OBJETO (lo que mandas desde Postman)
        // Ya no usamos "Admin123" fijo, usamos usuario.getPassword()
        String passwordEncriptada = passwordEncoder.encode(usuario.getPassword());

        // 3. Guardamos el hash en el usuario
        usuario.setPassword(passwordEncriptada);

        // 4. Aseguramos que el estado sea activo por defecto
        usuario.setEstado(true);

        return usuarioRepository.save(usuario);
    }
    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
    public Usuario actualizarUsuario(UUID id, Usuario nuevosDatos) {
        // 1. Buscamos el usuario actual en la base de datos
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        // 2. Actualizamos solo los campos permitidos
        // Mantenemos el ID y la contraseña original si no se envía una nueva
        usuarioExistente.setNombre(nuevosDatos.getNombre());
        usuarioExistente.setApellido(nuevosDatos.getApellido());
        usuarioExistente.setDni(nuevosDatos.getDni());
        usuarioExistente.setCorreo(nuevosDatos.getCorreo());
        usuarioExistente.setRol(nuevosDatos.getRol());
        usuarioExistente.setEstado(nuevosDatos.getEstado());

        // AQUÍ ESTÁ EL CAMPO QUE NECESITAS
        usuarioExistente.setTelefono(nuevosDatos.getTelefono());

        // Si mandas ubicación, se actualiza, si no, se queda la que estaba
        if (nuevosDatos.getUbicacionCasa() != null) {
            usuarioExistente.setUbicacionCasa(nuevosDatos.getUbicacionCasa());
        }

        // 3. Guardamos los cambios
        return usuarioRepository.save(usuarioExistente);
    }

    public void cambiarEstado(UUID id, Boolean estado) {
        Usuario u = usuarioRepository.findById(id).orElseThrow();
        u.setEstado(estado);
        usuarioRepository.save(u);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }
    public Usuario actualizarPorDni(String dni, Usuario nuevosDatos) {
        // 1. Buscamos al usuario por su DNI
        Usuario usuarioExistente = usuarioRepository.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con DNI: " + dni));

        // 2. Actualizamos los campos (como el teléfono)
        if (nuevosDatos.getNombre() != null) usuarioExistente.setNombre(nuevosDatos.getNombre());
        if (nuevosDatos.getApellido() != null) usuarioExistente.setApellido(nuevosDatos.getApellido());
        if (nuevosDatos.getTelefono() != null) usuarioExistente.setTelefono(nuevosDatos.getTelefono());
        if (nuevosDatos.getCorreo() != null) usuarioExistente.setCorreo(nuevosDatos.getCorreo());

        // Si mandas ubicación nueva, se actualiza
        if (nuevosDatos.getUbicacionCasa() != null) {
            usuarioExistente.setUbicacionCasa(nuevosDatos.getUbicacionCasa());
        }

        // 3. Guardamos los cambios sobre el mismo objeto
        return usuarioRepository.save(usuarioExistente);
    }
}