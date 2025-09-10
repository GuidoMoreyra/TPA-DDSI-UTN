package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.dto.HechoCsvDto;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public final class FuenteEstatica extends Fuente {

  private final String archivo;

  /// Se asume que los archivos fueron previamente normalizados.
  /// La ruta del archivo debe ser src/main/resources,
  ///  si no se lanzara una exepcion de tipo InvalidPathException.
  @Override
  public List<Hecho> obtenerHechos() {

    //Creo la ruta al archivo
    String rutaArchivo = "src/main/resources/" + archivo + ".csv";

    //Creamos el reader para leer el archivo
    try (
        BufferedReader reader = new BufferedReader(
            new InputStreamReader(new FileInputStream(rutaArchivo), StandardCharsets.UTF_8)
        );
    ) {
      //Usamos la libreria OpenCsv para leer todos los hechos del csv
      // y pasarlos a una lista de DTOs
      List<HechoCsvDto> dtos = new CsvToBeanBuilder<HechoCsvDto>(reader)
          .withType(HechoCsvDto.class)
          .withIgnoreLeadingWhiteSpace(true)
          .withSeparator(';')
          .build()
          .parse();

      //Creamos los hechos debidamente a partir de los datos del archivo
      return dtos
          .stream()
          .map(dto -> new Hecho(
              dto.getTitulo(),
              dto.getDescripcion(),
              dto.getCategoria(),
              dto.getLongitud(),
              dto.getLatitud(),
              dto.getFechaDelHecho(),
              OrigenHecho.ESTATICO,
              dto.getContenidoMultimedia(),
              dto.getHoraHecho()

          ))
          .toList();

    } catch (FileNotFoundException e) {
      throw new RuntimeException("Archivo no encontrado: ", e);
    } catch (IOException e) {
      throw new RuntimeException("Error al leer el archivo: ", e);
    }
  }

}




