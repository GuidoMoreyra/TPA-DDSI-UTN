package ar.edu.utn.frba.dds;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader; //para leer linea por linea
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException; //capturas de errores del formato CSV sin esto me tira error
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap; //ambas se usan para representar
import java.util.Iterator; //porque la clase implementa esta interfaz
import java.util.Map;    //cada fila como calve-valor


public class CsvIterator implements Iterator<Map<String, String>> {

  private final InputStream entradaArchivoCsv;


  public CsvIterator(InputStream entradaArchivoCsv)
      throws IOException, CsvValidationException {
    this.entradaArchivoCsv = entradaArchivoCsv;
  }


  @Override
  public boolean hasNext() {
    return false;
  }

  @Override
  public Map<String, String> next() {
    //crea un nuevo HashMap para
    //almacenar el par <encabezado,valor>
    //trim() se usa para eliminar los espacios en blanc
    //al principio y al final de una cadena de texto
    Map<String, String> map = new HashMap<>();

    try {
      //lectorDeFilas lee el archivo csv linea por linea
      CSVReader lectorDeFilas = new CSVReaderBuilder(
          new InputStreamReader(entradaArchivoCsv, StandardCharsets.UTF_8)
      )
          .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
          .build();
      //forma un string con la primera fila donde indica que columnas se tiene
      String[] encabezado = lectorDeFilas.readNext();

      if (encabezado != null) {
        String[] siguienteLinea = lectorDeFilas.readNext();

        while (siguienteLinea != null) {
          for (int i = 0; i < encabezado.length; i++) {
            map.put(encabezado[0].trim(), encabezado[i].trim());
            //por cada posicion valida,
            //agrega al mapa una entrada <columna,valor>
            siguienteLinea = lectorDeFilas.readNext();
          }
        }
      }
    } catch (IOException | CsvValidationException e) {
      //estuve obligado a ponerlas porque sino me tira error
      throw new RuntimeException("Error al leer la siguiente línea del CSV", e);
    }
    return map;
  }
}
