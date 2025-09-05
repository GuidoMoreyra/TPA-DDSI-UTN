package ar.edu.utn.frba.dds.contracts;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;


public interface AlgoritmoDeConsenso {


  public boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio);


}
