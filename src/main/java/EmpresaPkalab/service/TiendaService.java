package EmpresaPkalab.service;

import EmpresaPkalab.model.Tienda;
import EmpresaPkalab.repository.TiendaRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TiendaService {

    private final TiendaRepository tiendaRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    // 1. GUARDAR / REGISTRAR
    public Tienda guardarTienda(Tienda tienda, double latitud, double longitud) {
        Point puntoTienda = geometryFactory.createPoint(new Coordinate(longitud, latitud));
        tienda.setUbicacion(puntoTienda);

        // Aseguramos que el estado sea true al nacer
        if (tienda.getEstado() == null) {
            tienda.setEstado(true);
        }

        return tiendaRepository.save(tienda);
    }

    // 2. OBTENER TODAS
    public List<Tienda> obtenerTodas() {
        return tiendaRepository.findAll();
    }

    // 3. BUSCAR POR ID (Útil para actualizar o ver detalle)
    public Tienda obtenerPorId(UUID id) {
        return tiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con ID: " + id));
    }

    // 4. BUSCADOR FLEXIBLE (Para el Admin)
    public List<Tienda> buscarTiendas(String filtro) {
        return tiendaRepository.buscarTiendasFlex(filtro);
    }

    // 5. ELIMINAR
    public void eliminarTienda(UUID id) {
        Tienda tienda = obtenerPorId(id);
        tiendaRepository.delete(tienda);
    }
}