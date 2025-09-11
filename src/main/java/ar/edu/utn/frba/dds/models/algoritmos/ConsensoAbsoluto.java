package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class ConsensoAbsoluto implements AlgoritmoDeConsenso {

  private final List<Fuente> fuentesActivas;

  public ConsensoAbsoluto(List<Fuente> fuentesActivas) {
    this.fuentesActivas = new ArrayList<>(fuentesActivas);
  }

  @Override
  public boolean estaConsensuado(Hecho hecho) {
    long repeticiones = 1;
    return repeticiones == fuentesActivas.size();
  }

  @Override
  public boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio) {
    long repeticiones = hechosRepositorio.stream()
        .filter(hechorepo -> hechorepo.compararHecho(hecho))
        .count();

    return repeticiones == hechosRepositorio.size();
  }

}
