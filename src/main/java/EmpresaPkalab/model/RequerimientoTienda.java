package EmpresaPkalab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "requerimiento_tienda", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequerimientoTienda {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "tienda_id", nullable = false)
    private Tienda tienda;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "dia_semana") // Lo que dice "Lunes", "Martes" en tu Excel
    private String diaSemana;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    // Este es el corazón de la lógica:
    // Si el Excel dice "3 motorizados", crearemos 3 registros con n_motorizado 1, 2 y 3.
    @Column(name = "n_motorizado", nullable = false)
    private Integer nMotorizado;

    @Column
    private String estado = "PENDIENTE"; // PENDIENTE o ASIGNADO
}