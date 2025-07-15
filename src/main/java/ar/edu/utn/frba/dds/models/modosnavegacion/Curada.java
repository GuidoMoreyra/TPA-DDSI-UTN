package ar.edu.utn.frba.dds.models.modosnavegacion;

import ar.edu.utn.frba.dds.contracts.ModoDeNavegacion;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class Curada implements ModoDeNavegacion {
  private Coleccion coleccion;

  public Curada(Coleccion coleccion) {
    this.coleccion = coleccion;
  }

  @Override
  public List<Hecho> obtenerHechos() {
    return new ArrayList<>(
        coleccion.obtenerColeccion()//provisorio
    );
  }



}
