package EmpresaPkalab.service;

import EmpresaPkalab.dto.UsuarioDTO;
import EmpresaPkalab.model.Usuario;
import EmpresaPkalab.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
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
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    public Usuario buscarPorId(UUID id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario registrarUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        mapearDtoAEntidad(usuario, dto);

        // Encriptar contraseña solo en registro
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setEstado(true);

        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(UUID id, UsuarioDTO dto) {
        Usuario usuarioExistente = buscarPorId(id);
        mapearDtoAEntidad(usuarioExistente, dto);

        // Si mandan una nueva contraseña, se actualiza, si no, se queda la anterior
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return usuarioRepository.save(usuarioExistente);
    }

    private void mapearDtoAEntidad(Usuario usuario, UsuarioDTO dto) {
        usuario.setDni(dto.getDni());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setRol(dto.getRol());
        if (dto.getEstado() != null) usuario.setEstado(dto.getEstado());

        // Convertir Lat/Lng del Mapa a Point de PostGIS
        if (dto.getLatitud() != 0 && dto.getLongitud() != 0) {
            Point punto = geometryFactory.createPoint(new Coordinate(dto.getLongitud(), dto.getLatitud()));
            usuario.setUbicacionCasa(punto);
        }
    }

    public void cambiarEstado(UUID id, Boolean estado) {
        Usuario u = buscarPorId(id);
        u.setEstado(estado);
        usuarioRepository.save(u);
    }
}