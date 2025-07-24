package ar.edu.utn.frba.dds.contracts;

import java.util.List;

import ar.edu.utn.frba.dds.models.Hecho;

public interface AlgoritmoDeConsenso {


  public boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio);


}
