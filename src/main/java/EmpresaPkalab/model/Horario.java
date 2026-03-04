package EmpresaPkalab.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "horario")
@Data
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario; // El motorizado elegido

    @ManyToOne
    @JoinColumn(name = "tienda_id")
    private Tienda tienda; // La tienda asignada

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado = "ASIGNADO";
}