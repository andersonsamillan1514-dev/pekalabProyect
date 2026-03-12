package EmpresaPkalab.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TiendaDTO {
    private UUID id;
    private String nombreTienda;
    private String ruc;
    private String direccion;
    private Boolean estado;
    private Integer radioPermitidoMetros;

    // Estos dos campos son los que vienen del mapa de Google
    private double latitud;
    private double longitud;
}
