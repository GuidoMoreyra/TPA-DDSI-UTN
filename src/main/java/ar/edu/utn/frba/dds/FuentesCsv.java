package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.models.Hecho;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FuentesCsv implements LectorDeFuentes {
  private final String rutaArchivo;

  public FuentesCsv(String rutaArchivo) {
    this.rutaArchivo = rutaArchivo;
  }

  @Override
  public List<Hecho> obtenerFuentes() {
    List<Hecho> hechos = new ArrayList<>();
    File archivo = new File(this.rutaArchivo);
    String linea;
    try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
      while ((linea = br.readLine()) != null) {
        String[] datos = linea.split(",");
        if (linea.length() == 6) {
          continue;
        }
        double latitud = Double.parseDouble(datos[3]);
        double longitud = Double.parseDouble(datos[4]);
        LocalDate fecha = LocalDate.parse(datos[5]);
        hechos.add(new  Hecho(datos[0], datos[1], datos[2], latitud,
                                longitud, fecha, OrigenHecho.ESTATICA));
      }

    } catch (IOException e) {
      throw new RuntimeException("Error al leer el archivo CSV: " + rutaArchivo, e);
    }
    return hechos;
  }
}
