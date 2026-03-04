package EmpresaPkalab.repository;

import EmpresaPkalab.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface TiendaRepository extends JpaRepository<Tienda, UUID> {
    // El nombre debe ser este:
    Optional<Tienda> findByNombreTiendaIgnoreCase(String nombreTienda);
}