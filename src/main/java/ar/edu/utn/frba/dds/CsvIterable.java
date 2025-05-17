package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import com.opencsv.exceptions.CsvValidationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class CsvIterable implements Iterable<Hecho> {

  private final String archivo;

  public CsvIterable(String archivo) {
    this.archivo = archivo;
  }

  @Override
  public Iterator<Hecho> iterator() {
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
