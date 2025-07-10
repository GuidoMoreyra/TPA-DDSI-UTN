package ar.edu.utn.frba.dds.models.modosnavegacion;

import ar.edu.utn.frba.dds.contracts.ModoDeNavegacion;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.util.ArrayList;
import java.util.List;

public class Curada implements ModoDeNavegacion {

  @Override
  public List<Hecho> obtenerHechos() {
    return HechosRepository.getInstance().getHechos();
  }

  /* si se pudiera tener la lista de hechosConsensuados dentro de la coleccion
  *public List<Hecho> navegar(Coleccion coleccion) {
  *  return new ArrayList<>(coleccion.getHechosConsensuados()); // devuelve la copia actual
  *  } de esta manera todavia sigue recolectando hechos cada vez que se necesita.
  *
  * */

}
