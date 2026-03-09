package EmpresaPkalab.repository;

import EmpresaPkalab.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // IMPORTANTE
import org.springframework.data.repository.query.Param; // IMPORTANTE
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TiendaRepository extends JpaRepository<Tienda, UUID> {

    // Búsqueda exacta para el Excel
    Optional<Tienda> findByNombreTiendaIgnoreCase(String nombreTienda);

    // Búsqueda flexible para el Admin
    @Query("SELECT t FROM Tienda t WHERE LOWER(t.nombreTienda) LIKE LOWER(CONCAT('%', :filtro, '%')) OR t.ruc LIKE CONCAT('%', :filtro, '%')")
    List<Tienda> buscarTiendasFlex(@Param("filtro") String filtro);
}