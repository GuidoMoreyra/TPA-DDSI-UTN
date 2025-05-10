package ar.edu.utn.frba.dds;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

public class CsvIterable implements Iterable<Map<String, String>> {

  private final String archivo;

  public CsvIterable(String archivo) {
    this.archivo = archivo;
  }

  @Override
  public Iterator<Map<String, String>> iterator() {
    try {
      InputStream entradaArchivoCsv = obtenerInputStreamDelArchivo();

      //se lo pasa a la instacia de CsvIterator para seguir
      //leyendo el resto del archivo linea por linea
      return new CsvIterator(entradaArchivoCsv);

    } catch (IOException | CsvValidationException e) {
      throw new RuntimeException("Error al leer el archivo CSV", e);
    }
  }

  private InputStream obtenerInputStreamDelArchivo() {
    //abre el archivo desde resources
    // si no lo encuntra lanza una excepcion
    //reemplaza is == inputStream por entradaArchivoCsv
    InputStream entradaArchivoCsv = ClassLoader.getSystemResourceAsStream(archivo);
    if (entradaArchivoCsv == null) {
      throw new RuntimeException("Archivo no encontrado: " + archivo);
    }
    return entradaArchivoCsv;
  }
}
