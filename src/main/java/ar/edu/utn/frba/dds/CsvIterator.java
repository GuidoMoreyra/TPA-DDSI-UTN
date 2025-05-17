package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader; //para leer linea por linea
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException; //capturas de errores del formato CSV sin esto me tira error
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator; //porque la clase implementa esta interfaz


public class CsvIterator implements Iterator<Hecho> {

  private CSVReader lector;
  //herramienta de openCsv para leer el archivo
  // private final InputStream entradaArchivoCsv;
  private String[] encabezado;
  //guarda los nombres de columnas (primera fila del CSV)
  private String[] proximaLinea;
  //representa la proxima linea a procesar


  public CsvIterator(InputStream entradaArchivoCsv) throws IOException, CsvValidationException {
    //this.entradaArchivoCsv = entradaArchivoCsv;
    this.lector = new CSVReaderBuilder(
        new InputStreamReader(
            entradaArchivoCsv, StandardCharsets.UTF_8))
            .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
            .build();
    //se construye el CSVREADER con utf-8 y separador ;
    this.encabezado = lector.readNext();
    //primera linea: nombre de columans
    // se asume que hay encabezado
    this.proximaLinea = lector.readNext();
    //se posiciona en la proxima fila de datos
    //ahora se hace una sola vez y se mantiene el estado correctamente
  }


  @Override
  public boolean hasNext() {
    //devuelve true si hay una linea por leer
    //permite recorrer linea por linea
    return proximaLinea != null;
  }

  @Override
  public Hecho next() {
    //metodo que devuelve el proximo Hecho
    //del tipo clase Hecho
    //trim() se usa para eliminar los espacios en blanc
    //al principio y al final de una cadena de texto


    try {
      Hecho hecho = this.convertirFilaHecho(proximaLinea); //new HashMap<>();
      proximaLinea = lector.readNext(); //avanza a la proxima línea
      return hecho;

    } catch (IOException | CsvValidationException e) {
      //estuve obligado a ponerlas porque sino me tira error
      throw new RuntimeException("Error al leer la siguiente línea del CSV", e);
    }

  }

  private Hecho convertirFilaHecho(String[] fila) {
    String titulo = obtenerValor("titulo", fila);
    String descripcion = obtenerValor("descripcion", fila);
    String categoria = obtenerValor("categoria", fila);
    String latitud = obtenerValor("latitud", fila);
    String longitud = obtenerValor("longitud", fila);
    String fechaStr = obtenerValor("fecha_Del_Hecho", fila);
    //String origenStr = obtenerValor("origen", fila);

    Double unaLatitud = Double.parseDouble(latitud);
    Double unaLongitud = Double.parseDouble(longitud);
    //por el momento es el formato que tiene el csv de prueba futuras entregas a cambiar
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate fechaDelHecho = LocalDate.parse(fechaStr, formatter);

    Hecho hecho = new Hecho(titulo, descripcion, categoria, unaLatitud, unaLongitud, fechaDelHecho);

    return hecho;
  }

  private String obtenerValor(String campo, String[] fila) {
    String valor = "";
    for (int i = 0; i < encabezado.length; i++) {
      if (encabezado[i].equalsIgnoreCase(campo)) {
        valor = i < fila.length ? fila[i].trim() : "";
        break;

      }
    }
    return valor;
  }
}
