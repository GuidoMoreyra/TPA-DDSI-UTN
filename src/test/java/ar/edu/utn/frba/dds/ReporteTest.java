package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.reportes.ReporteColeccion;
import org.junit.jupiter.api.Test;

public class ReporteTest {

  @Test
  void generarCsv_deberiaIncluirEncabezadoCorrecto() {
    ReporteColeccion reporte =
        new ReporteColeccion("CategoriaX", Provincia.BUENOS_AIRES, 12, Provincia.CORDOBA);

    String csv = reporte.generarCsv();

    /*startsWith: asegura que el CSV arranque con el encabezado correcto.*/
    assertTrue(
        csv.startsWith(
            "CategoriaConMasHechos,ProvinciaConMasHechos,horaPicoDeHechos,ProvinciaSegunCategoria"),
        "El encabezado del CSV no es correcto");
  }

  @Test
  void generarCsv_deberiaIncluirFilaConDatos() {
    ReporteColeccion reporte =
        new ReporteColeccion("Robo", Provincia.SANTA_FE, 18, Provincia.MENDOZA);

    String esperado = "Robo," + "," + "SANTA_FE" + "," + 18 + "," + "MENDOZA\n";
    String csv = reporte.generarCsv();

    /*asegura que en algún lugar aparezca la fila con los datos interpolados*/
    assertTrue(csv.contains(esperado), "La fila de datos no coincide con lo esperado");
  }

  @Test
  void generarCsv_deberiaTerminarConSaltoDeLinea() {
    ReporteColeccion reporte = new ReporteColeccion("Fraude", Provincia.CABA, 8, Provincia.CABA);

    String csv = reporte.generarCsv();

    /*asegura que el texto termina con \n.*/
    assertTrue(csv.endsWith("\n"), "El CSV debería terminar con salto de línea");
  }
}
