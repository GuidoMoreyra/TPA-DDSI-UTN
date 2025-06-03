package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class FuenteIntermedia implements Fuente {
  private List<Hecho> listaVacia = new ArrayList<>();

  public List<Hecho> obtenerHechos() {
    return listaVacia;
  }

}
