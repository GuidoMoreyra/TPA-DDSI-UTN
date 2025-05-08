package ar.edu.utn.frba.dds;

import com.opencsv.CSVReader; //para leer linea por linea
import com.opencsv.exceptions.CsvValidationException;//capturas de errores del formato CSV sin esto me tira error

import java.io.IOException;
import java.util.HashMap;//ambas se usan para representar
import java.util.Map;    //cada fila como calve-valor
import java.util.Iterator;//porque la clase implementa esta interfaz

public class CsvIterator implements Iterator<Map<String, String>> {

  private CSVReader lectorDeFilas;
  private String[] encabezado;//la fila de nombres de columnas (la primera línea del archivo)
  private String[] nextLine; //es la proxima linea que se procesa

  public CsvIterator(CSVReader reader, String[] encabezado) throws IOException, CsvValidationException {
    this.lectorDeFilas = reader;
    this.encabezado = encabezado;
    this.nextLine = reader.readNext(); // lee la primera línea de datos
  }

  @Override
  public boolean hasNext() {
    //devuelve true si hay una linea
    // lista para procesar
    return nextLine != null;
  }

  @Override
  public Map<String, String> next() {
    //crea un nuevo HashMap para
    //almacenar el par <encabezado,valor>
    //trim() se usa para eliminar los espacios en blanc
    //al principio y al final de una cadena de texto
    Map<String, String> map = new HashMap<>();
    for (int i = 0; i < encabezado.length && i < nextLine.length; i++) {
      map.put(encabezado[i].trim(), nextLine[i].trim());
      //por cada posicion valida,
      //agrega al mapa una entrada <columna,valor>
    }
    try {
      nextLine = lectorDeFilas.readNext(); // lee la siguiente línea
    } catch (IOException | CsvValidationException e) {
      //estuve obligado a ponerlas porque sino me tira error
      throw new RuntimeException("Error al leer la siguiente línea del CSV", e);
    }
    return map;
  }
}
