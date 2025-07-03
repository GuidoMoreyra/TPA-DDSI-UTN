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
    //hechosLocales = coleccion.aplicarAlgoritmoDeConsenso();

    return new ArrayList<>(hechosLocales);
  }

  /* si se pudiera tener la lista de hechosConsensuados dentro de la coleccion
  *public List<Hecho> navegar(Coleccion coleccion) {
  *  return new ArrayList<>(coleccion.getHechosConsensuados()); // devuelve la copia actual
  *  } de esta manera todavia sigue recolectando hechos cada vez que se necesita.
  *
  * */

}
