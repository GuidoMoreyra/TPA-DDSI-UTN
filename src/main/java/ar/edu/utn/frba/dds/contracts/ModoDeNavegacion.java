package ar.edu.utn.frba.dds.contracts;

import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;

public interface ModoDeNavegacion {

  public List<Hecho> obtenerHechos();
}
