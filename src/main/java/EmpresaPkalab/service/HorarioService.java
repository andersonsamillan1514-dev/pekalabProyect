package EmpresaPkalab.service;

import EmpresaPkalab.model.Horario;
import EmpresaPkalab.repository.HorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HorarioService {

    private final HorarioRepository horarioRepository;

    /**
     * GUARDA LA ASIGNACIÓN (El "Match" entre motorizado y tienda)
     */
    public Horario guardarHorario(Horario horario) {
        // Validación opcional: Verificar si el motorizado ya tiene un turno ese día a esa hora
        boolean hayCruce = horarioRepository.existeCruceHorario(
                horario.getUsuario().getId(),
                horario.getFecha(),
                horario.getHoraInicio(),
                horario.getHoraFin()
        );

        if (hayCruce) {
            throw new RuntimeException("El motorizado ya tiene una asignación que se cruza con este horario.");
        }

        horario.setEstado("ASIGNADO");
        return horarioRepository.save(horario);
    }

    /**
     * EDITA LAS HORAS (Para el caso de la hora extra que mencionaste)
     */
    public Horario actualizarHoras(UUID horarioId, LocalTime nuevaHoraInicio, LocalTime nuevaHoraFin) {
        Horario horario = horarioRepository.findById(horarioId)
                .orElseThrow(() -> new RuntimeException("El registro de horario no existe"));

        horario.setHoraInicio(nuevaHoraInicio);
        horario.setHoraFin(nuevaHoraFin);

        return horarioRepository.save(horario);
    }

    /**
     * LISTAR HORARIOS (Para que el Admin vea la agenda completa)
     */
    public List<Horario> listarTodos() {
        return horarioRepository.findAll();
    }
}