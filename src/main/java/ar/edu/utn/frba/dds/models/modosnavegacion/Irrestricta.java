package ar.edu.utn.frba.dds.models.modosnavegacion;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.contracts.ModoDeNavegacion;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;


public class Irrestricta implements ModoDeNavegacion {
  private Coleccion coleccion;

  public Irrestricta(Coleccion coleccion) {
    this.coleccion = coleccion;
  }

  @Override
  public List<Hecho> obtenerHechos() {
    return new ArrayList<>(coleccion
        .obtenerColeccion());
  }
}
