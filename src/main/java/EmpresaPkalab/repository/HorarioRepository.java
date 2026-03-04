package EmpresaPkalab.repository;

import EmpresaPkalab.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, UUID> {

    // Para que el motorizado vea su agenda
    List<Horario> findByUsuarioId(UUID usuarioId);

    // Para que el administrador vea quiénes trabajan hoy en todas las tiendas
    List<Horario> findByFecha(LocalDate fecha);

    /**
     * Esta consulta es vital:
     * Verifica si un motorizado específico ya tiene un turno que se cruza
     * con el horario que el administrador intenta asignar.
     */
    @Query("SELECT COUNT(h) > 0 FROM Horario h " +
            "WHERE h.usuario.id = :usuarioId " +
            "AND h.fecha = :fecha " +
            "AND (:inicio < h.horaFin AND :fin > h.horaInicio)")
    boolean existeCruceHorario(@Param("usuarioId") UUID usuarioId,
                               @Param("fecha") LocalDate fecha,
                               @Param("inicio") LocalTime inicio,
                               @Param("fin") LocalTime fin);
}