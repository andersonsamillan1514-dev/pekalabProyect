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

@Service
@RequiredArgsConstructor
public class RequerimientoService {

    private final RequerimientoRepository requerimientoRepository;
    private final TiendaRepository tiendaRepository;

    @Transactional
    public void importarDesdeExcel(MultipartFile archivo) throws Exception {
        Workbook workbook = new XSSFWorkbook(archivo.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        System.out.println(">>> Iniciando lectura de Excel. Filas detectadas: " + sheet.getLastRowNum());

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row fila = sheet.getRow(i);

            // Validamos que la celda de la tienda (Columna A) no sea nula
            if (fila == null || fila.getCell(0) == null) {
                continue;
            }

            try {
                // 1. LEER DATOS DEL EXCEL (Ajustado a columnas separadas)
                String nombreTienda = fila.getCell(0).getStringCellValue().trim();

                // Columna B: Fecha
                LocalDate fecha = fila.getCell(1).getLocalDateTimeCellValue().toLocalDate();

                // Columna C: Día (opcional, lo leemos como String)
                String diaSemana = fila.getCell(2).getStringCellValue();

                // Columna D: Hora Inicio
                LocalTime horaInicio = fila.getCell(3).getLocalDateTimeCellValue().toLocalTime();

                // Columna E: Hora Fin
                LocalTime horaFin = fila.getCell(4).getLocalDateTimeCellValue().toLocalTime();

                // Columna F: Cantidad de Motos
                int cantidadMotos = (int) fila.getCell(5).getNumericCellValue();

                // 2. BUSCAR LA TIENDA
                Tienda tienda = tiendaRepository.findByNombreTiendaIgnoreCase(nombreTienda)
                        .orElseThrow(() -> new RuntimeException("Tienda '" + nombreTienda + "' no encontrada"));

                System.out.println(">>> Procesando " + nombreTienda + ": " + cantidadMotos + " cupos para el " + fecha);

                // 3. CREAR LOS CUPOS
                for (int j = 1; j <= cantidadMotos; j++) {
                    RequerimientoTienda cupo = new RequerimientoTienda();
                    cupo.setTienda(tienda);
                    cupo.setFecha(fecha);
                    cupo.setDiaSemana(diaSemana);
                    cupo.setHoraInicio(horaInicio);
                    cupo.setHoraFin(horaFin);
                    cupo.setNMotorizado(j); // O el número que corresponda

                    requerimientoRepository.save(cupo);
                }
                System.out.println(">>> Fila " + (i + 1) + " guardada.");

            } catch (Exception e) {
                // Este print es vital para saber qué falló exactamente
                System.err.println("!!! ERROR EN FILA " + (i + 1) + ": " + e.getMessage());
                throw e; // Lanzamos la excepción para que el @Transactional haga rollback si algo falla
            }
        }
        workbook.close();
    }
}