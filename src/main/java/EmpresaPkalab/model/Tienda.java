package EmpresaPkalab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import java.util.UUID;

@Entity
@Table(name = "tienda")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "nombre_tienda", unique = true, nullable = false)
    private String nombreTienda;

    //AGREGAR COLUMNA RUC
    @Column(name="ruc", length = 11)
    private String ruc;

    //COLUMNA DE ESTADO
    @Column(name= "estado")
    private Boolean estado=true;
    
    private String direccion;

    // Ubicación GPS de la tienda para calcular distancias
    @Column(columnDefinition = "geography(Point, 4326)")
    private Point ubicacion;

    @Column(name = "radio_permitido_metros")
    private Integer radioPermitidoMetros = 100; // Para el marcado de asistencia
}