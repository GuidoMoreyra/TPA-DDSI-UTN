package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.Coleccion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ColeccionRepositorio {

  private static final ColeccionRepositorio INSTANCE = new ColeccionRepositorio();
  private final List<Coleccion> colecciones = new ArrayList<>();

  private ColeccionRepositorio() {}

  public static ColeccionRepositorio getInstance() {
    return INSTANCE;
  }


  public List<Coleccion> getColecciones() {
    return Collections.unmodifiableList(colecciones);
  }

  public void agregarColeccion(Coleccion unaColeccion) {
    colecciones.add(unaColeccion);
  }

  public void limpiar() { //para testear
    this.colecciones.clear();
  }

}
