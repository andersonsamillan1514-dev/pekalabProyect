package EmpresaPkalab.repository;

import EmpresaPkalab.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    // Esto es vital para que el login funcione después
    Optional<Usuario> findByCorreo(String correo);

    // NUEVO: Para buscar motorizados/clientes por DNI
    Optional<Usuario> findByDni(String dni);
}