package ar.edu.utn.frba.dds;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

public class FuenteEstatica implements Fuente {

  private String archivo; //podria ser final
                          //guarda el hombre del archivo csv
  //este se encuentra en src/main/resources

  public FuenteEstatica(String archivo) {
    this.archivo = archivo;
  }
  public void mostrarHechos() {
    for (Map<String, String> hecho : this.LeerCsv()) {
      System.out.println("Hecho:");
      hecho.forEach((clave, valor) -> System.out.println("  " + clave + ": " + valor));
      System.out.println();
    }
  }

  public void mostrarHechosQueCumplen(Criterio unCriterio) {
    for (Map<String, String> hecho : this.LeerCsv()) {
      if (unCriterio.seCumpleCriterio(hecho)) {
        System.out.println("Hecho que cumplen con un criterio:");
        hecho.forEach((clave, valor) -> System.out.println("  " + clave + ": " + valor));
        System.out.println();
      }
    }
  }

  @Override
  public Iterable<Map<String, String>> LeerCsv() {
    return new Iterable<>() {
      @Override
      public Iterator<Map<String, String>> iterator() {
        try {
          //abre el archivo desde resources
          // si no lo encuntra lanza una excepcion
          InputStream is = ClassLoader.getSystemResourceAsStream(archivo);
          if (is == null) throw new RuntimeException("Archivo no encontrado: " + archivo);
          //usa openCSV para crear un lector con ; como separador
          //el ejemplo de archivo csv utiliza ;
          //si no lo pongo tira error
          CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is))
              .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
              .build();

          String[] encabezado = reader.readNext(); // primera línea: encabezado

          return new CsvIterator(reader, encabezado);
          //Devuelve un CsvIterator
          //configurado con ese lector y encabezado.

        } catch (IOException | CsvValidationException e) {
          throw new RuntimeException("Error al leer el archivo CSV", e);
        }
      }
    };
  }

}


