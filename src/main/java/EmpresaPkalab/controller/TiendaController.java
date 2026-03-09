package EmpresaPkalab.controller;

import EmpresaPkalab.model.Tienda;
import EmpresaPkalab.repository.TiendaRepository; // Importación necesaria
import EmpresaPkalab.service.TiendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
@RequiredArgsConstructor // Esta anotación inyectará ambos automáticamente
public class TiendaController {

    private final TiendaService tiendaService;
    private final TiendaRepository tiendaRepository; // <--- FALTA ESTA LÍNEA

    @PostMapping("/registrar")
    public ResponseEntity<Tienda> registrarTienda(
            @RequestBody Tienda tienda,
            @RequestParam double latitud,
            @RequestParam double longitud) {
        return ResponseEntity.ok(tiendaService.guardarTienda(tienda, latitud, longitud));
    }

    @GetMapping
    public ResponseEntity<List<Tienda>> listarTiendas() {
        return ResponseEntity.ok(tiendaService.obtenerTodas());
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Tienda>> buscar(@RequestParam String filtro) {
        // Asegúrate de que el método se llame buscarTiendasFlex (con 's' y 's')
        // tal como lo pusiste en tu Repository
        return ResponseEntity.ok(tiendaRepository.buscarTiendasFlex(filtro));
    }
}