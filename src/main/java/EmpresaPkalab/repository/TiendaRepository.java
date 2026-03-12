package EmpresaPkalab.repository;

import EmpresaPkalab.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, UUID> {

    Optional<Tienda> findByNombreTienda(String nombreTienda);

    // AGREGA ESTE MÉTODO PARA QUE EL SERVICE NO DE ERROR
    Optional<Tienda> findByNombreTiendaIgnoreCase(String nombreTienda);

    List<Tienda> findByNombreTiendaContainingIgnoreCase(String nombreTienda);
}