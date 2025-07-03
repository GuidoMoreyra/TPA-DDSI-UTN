package ar.edu.utn.frba.dds.models.modosnavegacion;

import ar.edu.utn.frba.dds.contracts.ModoDeNavegacion;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class Curada implements ModoDeNavegacion {
  private List<Hecho> hechosLocales;

  @Override
  public List<Hecho> navegar(Coleccion coleccion) {
    hechosLocales = coleccion.aplicarAlgoritmoDeConsenso(coleccion);

    return new ArrayList<>(hechosLocales);
  }
}
