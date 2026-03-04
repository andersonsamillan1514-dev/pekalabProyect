package EmpresaPkalab.controller;

import EmpresaPkalab.model.Tienda;
import EmpresaPkalab.service.TiendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tiendas")
@RequiredArgsConstructor
public class TiendaController {

    private final TiendaService tiendaService;

    // POST: Para registrar tiendas con GPS (Ya lo tenías)
    @PostMapping("/registrar")
    public ResponseEntity<Tienda> registrarTienda(
            @RequestBody Tienda tienda,
            @RequestParam double latitud,
            @RequestParam double longitud) {
        return ResponseEntity.ok(tiendaService.guardarTienda(tienda, latitud, longitud));
    }

    // GET: El que necesitabas para ver todos los nombres antes del Excel
    @GetMapping
    public ResponseEntity<List<Tienda>> listarTiendas() {
        List<Tienda> tiendas = tiendaService.obtenerTodas();
        return ResponseEntity.ok(tiendas);
    }
}