package EmpresaPkalab.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class UsuarioDTO {
    private UUID id; // Para cuando toque editar
    private String dni;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;
    private String telefono;
    private String rol;
    private Boolean estado;

    // Aquí recibimos lo que viene del Pin del Mapa y el Buscador
    private double latitud;
    private double longitud;
}