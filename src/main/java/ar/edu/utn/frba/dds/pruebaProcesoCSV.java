package ar.edu.utn.frba.dds;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReaderBuilder;

import com.opencsv.CSVReader;//importo la clase CSVReader de la biblioteca OpenCSV, que facilita la lectura de archivos CSV


import java.io.InputStream;//sirve para leer datos en bruto
import java.io.InputStreamReader;//conveirte esos datos en caracteres (para que CSVReader pueda leerlos como texto


public class pruebaProcesoCSV {
  public static void main(String[] args) throws Exception {
    //Busca y abre el archivo ejemploTp.csv desde el directorio resources del proyecto.
    InputStream inputStream = ClassLoader.getSystemResourceAsStream("ejemploTp.csv");

    //si inputstream es null, muestra el mensaje y termina el programa.
    if (inputStream == null) {
      System.out.println("No se encontró el archivo CSV en resources.");
      return;
    }
    //Crea un CSVReader pasándole un InputStreamReader, que convierte el flujo de bytes (inputStream) en texto legible para el lector de CSV.
    CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream))
        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
        .build();

    int contador = 0;// Inicializa una variable count para llevar la cuenta de cuántas líneas se leyeron.
    String[] linea;//Declara una variable linea que almacenará cada línea del CSV como un arreglo de strings (una celda por posición del arreglo).
    int maxlineas = 100; //limite de seguridad
    //while lee linia por linea del archivo CSV
    //Haya líneas disponibles (readNext() devuelve null si termina el archivo).
    while ((linea = csvReader.readNext()) != null && contador < 10 && maxlineas-- > 0) {
      if(linea.length == 0) continue;
      System.out.println(String.join(" | ", linea));
      contador++;
    }

    csvReader.close();//libera recursos cerrando CSVReader
  }
}
/*
public class LectorCsv {

  public static void leerCsv(String nombreArchivo) throws Exception {
    InputStream inputStream = ClassLoader.getSystemResourceAsStream(nombreArchivo);

    if (inputStream == null) {
      System.out.println("No se encontró el archivo CSV en resources.");
      return;
    }

    CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));

    int count = 0;
    String[] linea;
    while ((linea = csvReader.readNext()) != null && count < 10) {
      System.out.println(String.join(" | ", linea));
      count++;
    }

    csvReader.close();
  }
}
public interface Fuente {
    Iterable<Map<String, String>> hechos(); // sin filtro por ahora
}
public class FuenteCSV implements Fuente {
    private final String archivo;

    public FuenteCSV(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public Iterable<Map<String, String>> hechos() {
        return () -> {
            try {
                InputStream is = ClassLoader.getSystemResourceAsStream(archivo);
                if (is == null) throw new RuntimeException("Archivo no encontrado");

                CSVReader reader = new CSVReaderBuilder(new InputStreamReader(is))
                        .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                        .build();

                String[] encabezado = reader.readNext();
                return new Iterator<>() {
                    String[] nextLine = reader.readNext();

                    @Override
                    public boolean hasNext() {
                        return nextLine != null;
                    }

                    @Override
                    public Map<String, String> next() {
                        Map<String, String> map = new HashMap<>();
                        for (int i = 0; i < encabezado.length && i < nextLine.length; i++) {
                            map.put(encabezado[i].trim(), nextLine[i].trim());
                        }
                        try {
                            nextLine = reader.readNext();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        return map;
                    }
                };
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
public class TestFuente {
    public static void main(String[] args) {
        Fuente fuente = new FuenteCSV("ejemploTp.csv");
        for (Map<String, String> hecho : fuente.hechos()) {
            System.out.println(hecho);
        }
    }
}


*/

