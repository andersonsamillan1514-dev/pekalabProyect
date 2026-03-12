package EmpresaPkalab.controller;

import EmpresaPkalab.dto.TiendaDTO;
import EmpresaPkalab.model.Tienda;
import EmpresaPkalab.service.TiendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tiendas")
@RequiredArgsConstructor
public class TiendaController {

    private final TiendaService tiendaService;

    @GetMapping
    public ResponseEntity<List<Tienda>> listar() {
        return ResponseEntity.ok(tiendaService.listarTodas());
    }

    // Buscador por nombre (Ej: /api/tiendas/buscar?nombre=surco)
    @GetMapping("/buscar")
    public ResponseEntity<List<Tienda>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(tiendaService.buscarPorNombre(nombre));
    }

    @PostMapping("/registrar")
    public ResponseEntity<Tienda> registrar(@RequestBody TiendaDTO tiendaDTO) {
        return ResponseEntity.ok(tiendaService.registrarTienda(tiendaDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tienda> editar(@PathVariable UUID id, @RequestBody TiendaDTO tiendaDTO) {
        return ResponseEntity.ok(tiendaService.actualizarTienda(id, tiendaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable UUID id) {
        tiendaService.eliminarTienda(id);
        return ResponseEntity.noContent().build();
    }
}