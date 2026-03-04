package EmpresaPkalab.repository;

import EmpresaPkalab.model.RequerimientoTienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RequerimientoRepository extends JpaRepository<RequerimientoTienda, UUID> {
}