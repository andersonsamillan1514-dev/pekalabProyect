package EmpresaPkalab.controller;

import EmpresaPkalab.service.RequerimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/requerimientos")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RequerimientoController {

    private final RequerimientoService requerimientoService;

    @PostMapping("/importar")
    public ResponseEntity<String> importarExcel(@RequestParam("file") MultipartFile file) {
        try {
            requerimientoService.importarDesdeExcel(file);
            return ResponseEntity.ok("Excel procesado: Cupos creados individualmente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}