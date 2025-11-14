package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.*;

import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.reportes.ReporteColeccion;
import ar.edu.utn.frba.dds.utils.ExportadorCsv;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ExportadorCsvTest {

  // genera una carpeta temporal
  @TempDir Path tempDir;

  @Test
  void exportarCsvArchivo_deberiaCrearArchivoConContenido() throws IOException {
    // Arrange
    ReporteColeccion reporte =
        new ReporteColeccion("Robo", Provincia.BUENOS_AIRES, 15, Provincia.CORDOBA);

    Path archivoCsv = tempDir.resolve("reporte.csv");
    ExportadorCsv exportador = new ExportadorCsv();

    // Act
    exportador.exportarCsvArchivo(archivoCsv.toString(), reporte);

    // Assert
    assertTrue(Files.exists(archivoCsv), "El archivo CSV no fue creado");

    String contenido = Files.readString(archivoCsv);
    assertEquals(
        reporte.generarCsv(),
        contenido,
        "El contenido del CSV no coincide con el generado por Reporte");
  }

  @Test
  void exportarCsvArchivo_deberiaCrearDirectoriosSiNoExisten() {
    // Arrange
    Path carpeta = tempDir.resolve("subdir/nivel2");
    Path archivoCsv = carpeta.resolve("reporte.csv");

    ReporteColeccion reporte = new ReporteColeccion("Fraude", Provincia.CABA, 10, Provincia.CABA);
    ExportadorCsv exportador = new ExportadorCsv();

    // Act
    exportador.exportarCsvArchivo(archivoCsv.toString(), reporte);

    // Assert
    assertTrue(Files.exists(archivoCsv), "El archivo no fue creado en subdirectorios");
  }
}
