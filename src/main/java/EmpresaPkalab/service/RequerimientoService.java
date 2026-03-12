package EmpresaPkalab.service;

import EmpresaPkalab.model.RequerimientoTienda;
import EmpresaPkalab.model.Tienda;
import EmpresaPkalab.repository.RequerimientoRepository;
import EmpresaPkalab.repository.TiendaRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequerimientoService {

    private final RequerimientoRepository requerimientoRepository;
    private final TiendaRepository tiendaRepository;

    public List<RequerimientoTienda> listarTodo() {
        return requerimientoRepository.findAllByOrderByFechaAscHoraInicioAsc();
    }

    @Transactional
    public void importarDesdeExcel(MultipartFile archivo) throws Exception {
        Workbook workbook = new XSSFWorkbook(archivo.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row fila = sheet.getRow(i);
            if (fila == null || fila.getCell(0) == null) continue;

            try {
                String nombreTienda = fila.getCell(0).getStringCellValue().trim();
                LocalDate fecha = fila.getCell(1).getLocalDateTimeCellValue().toLocalDate();
                String diaSemana = fila.getCell(2).getStringCellValue();
                LocalTime horaInicio = fila.getCell(3).getLocalDateTimeCellValue().toLocalTime();
                LocalTime horaFin = fila.getCell(4).getLocalDateTimeCellValue().toLocalTime();
                int cantidadMotos = (int) fila.getCell(5).getNumericCellValue();

                Tienda tienda = tiendaRepository.findByNombreTiendaIgnoreCase(nombreTienda)
                        .orElseThrow(() -> new RuntimeException("Tienda '" + nombreTienda + "' no encontrada"));

                for (int j = 1; j <= cantidadMotos; j++) {
                    RequerimientoTienda cupo = new RequerimientoTienda();
                    cupo.setTienda(tienda);
                    cupo.setFecha(fecha);
                    cupo.setDiaSemana(diaSemana);
                    cupo.setHoraInicio(horaInicio);
                    cupo.setHoraFin(horaFin);
                    cupo.setNMotorizado(j);
                    cupo.setEstado("PENDIENTE");
                    requerimientoRepository.save(cupo);
                }
            } catch (Exception e) {
                System.err.println("Error en fila " + (i + 1) + ": " + e.getMessage());
                // Podrías elegir continuar o frenar todo el proceso
            }
        }
        workbook.close();
    }
}