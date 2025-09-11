package ar.edu.utn.frba.dds.contracts;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;

public interface AlgoritmoDeConsenso {

  boolean estaConsensuado(Hecho hecho);

  boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio);

}
