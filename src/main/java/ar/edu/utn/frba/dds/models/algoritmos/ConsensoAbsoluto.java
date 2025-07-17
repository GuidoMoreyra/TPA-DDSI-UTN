package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsensoAbsoluto implements AlgoritmoDeConsenso {

  private List<Fuente> fuentesActivas;

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
