package EmpresaPkalab.service;

import EmpresaPkalab.model.Tienda;
import EmpresaPkalab.repository.TiendaRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.stereotype.Service;
import java.util.List; // Importante para que reconozca List

@Service
@RequiredArgsConstructor
public class TiendaService {

    private final TiendaRepository tiendaRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    public Tienda guardarTienda(Tienda tienda, double latitud, double longitud) {
        Point puntoTienda = geometryFactory.createPoint(new Coordinate(longitud, latitud));
        tienda.setUbicacion(puntoTienda);
        return tiendaRepository.save(tienda);
    }

    // --- ESTE ES EL MÉTODO QUE FALTABA ---
    public List<Tienda> obtenerTodas() {
        return tiendaRepository.findAll();
    }
}