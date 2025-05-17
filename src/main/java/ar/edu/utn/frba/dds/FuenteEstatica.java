package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class FuenteEstatica implements Fuente {

  private String archivo; //podria ser final
  //guarda el nombre del archivo csv
  //este se encuentra en src/main/resources

  public FuenteEstatica(String archivo) {
    this.archivo = archivo;
  }

  public void mostrarHechos() {
    for (Hecho hecho : this.obtenerHechos()) {
      imprimirHechos(hecho, "Hecho: ");
    }
  }

  public void mostrarHechosQueCumplen(Criterio unCriterio) {
    //se usa internamente el CsvIterator para recorrer los datos línea por línea.
    for (Hecho hecho : this.obtenerHechos()) {
      if (unCriterio.cumple(hecho)) {
        imprimirHechos(hecho, "Hecho que cumple con un criterio: ");
      }
    }
  }

  private void imprimirHechos(Hecho hecho, String mensaje) {
    System.out.println(mensaje);
    System.out.println("  Título: " + hecho.getTitulo());
    System.out.println("  Descripción: " + hecho.getDescripcion());
    System.out.println("  Categoría: " + hecho.getCategoria());
    System.out.println("  Latitud: " + hecho.getLatitud());
    System.out.println("  Longitud: " + hecho.getLongitud());
    System.out.println("  Fecha del Hecho: " + hecho.getFechaDelHecho());
    System.out.println("  Fecha de Creación: " + hecho.getFechaCreacion());
    //System.out.println("  Origen: " + hecho.getOrigen());
    System.out.println("  Estado: " + hecho.getEstado());
    System.out.println();
  }


  @Override
  public Iterable<Hecho> obtenerHechos() {
    return new CsvIterable(archivo);
  }

}


