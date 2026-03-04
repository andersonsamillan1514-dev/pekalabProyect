package EmpresaPkalab.controller;

import EmpresaPkalab.model.Usuario;
import EmpresaPkalab.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    // 1. Registrar
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.registrarUsuario(usuario));
    }

    // 2. Listar todos
    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    // 3. BUSCAR POR ID (El que estabas probando)
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    // 4. EDITAR (El que te daba error de PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editar(@PathVariable UUID id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuario));
    }

    // 5. Desactivar/Activar
    @PatchMapping("/{id}/estado")
    public ResponseEntity<String> cambiarEstado(@PathVariable UUID id, @RequestParam Boolean activo) {
        usuarioService.cambiarEstado(id, activo);
        return ResponseEntity.ok("Estado actualizado");
    }
    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscar(@RequestParam String dni) {
        return usuarioService.buscarPorDni(dni)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    // Editar usando el DNI en la URL
    @PutMapping("/dni/{dni}")
    public ResponseEntity<Usuario> editarPorDni(@PathVariable String dni, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.actualizarPorDni(dni, usuario));
    }
}