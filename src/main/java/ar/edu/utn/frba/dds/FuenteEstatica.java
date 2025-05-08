package ar.edu.utn.frba.dds;
import java.util.Map;

public class FuenteEstatica implements Fuente {

  private String archivo; //podria ser final
                          //guarda el hombre del archivo csv
  //este se encuentra en src/main/resources

  public FuenteEstatica(String archivo) {
    this.archivo = archivo;
  }
  public void mostrarHechos() {
    for (Map<String, String> hecho : this.leerCsv()) {
      imprimirHechos(hecho, "Hecho: ");
    }
  }

  public void mostrarHechosQueCumplen(Criterio unCriterio) {
    //se usa internamente el CsvIterator para recorrer los datos línea por línea.
    for (Map<String, String> hecho : this.leerCsv()) {
      if (unCriterio.seCumpleCriterio(hecho)) {
        imprimirHechos(hecho, "Hecho que cumple con un criterio: ");
      }
    }
  }
  private void imprimirHechos(Map<String, String> hecho, String mensaje) {
    System.out.println(mensaje);
    hecho.forEach((clave, valor) -> System.out.println("  " + clave + ": " + valor));
    System.out.println();
  }


  @Override
  public Iterable<Map<String, String>> leerCsv() {
    return new CsvIterable(archivo);
  }

}


