package EmpresaPkalab.controller;

import EmpresaPkalab.model.Horario;
import EmpresaPkalab.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/horarios")
@RequiredArgsConstructor
public class HorarioController {

    private final HorarioService horarioService;

    /**
     * Este es el que usarás para asignar un motorizado a una tienda
     */
    @PostMapping("/asignar")
    public ResponseEntity<Horario> crearHorario(@RequestBody Horario horario) {
        return ResponseEntity.ok(horarioService.guardarHorario(horario));
    }

    /**
     * ¡Aquí está lo que pediste! Editar horas si la tienda pide tiempo extra
     */
    @PutMapping("/editar-horas/{id}")
    public ResponseEntity<Horario> editarHoras(
            @PathVariable UUID id,
            @RequestParam String inicio, // Formato "HH:mm"
            @RequestParam String fin) {

        LocalTime horaInicio = LocalTime.parse(inicio);
        LocalTime horaFin = LocalTime.parse(fin);

        return ResponseEntity.ok(horarioService.actualizarHoras(id, horaInicio, horaFin));
    }
}