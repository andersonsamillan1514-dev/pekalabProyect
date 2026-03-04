package EmpresaPkalab.repository;

import EmpresaPkalab.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, UUID> {

    // Este nos servirá para ver el historial de un usuario específico
    List<Asistencia> findByUsuarioId(UUID usuarioId);

    // Este nos servirá para buscar si el usuario ya marcó entrada en un horario específico
    boolean existsByHorarioId(UUID horarioId);
}