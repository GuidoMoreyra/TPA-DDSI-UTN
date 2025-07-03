package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConsensoAbsoluto implements AlgoritmoDeConsenso {

  private List<Fuente> fuentesActivas;

  public  ConsensoAbsoluto(List<Fuente> fuentes) {
    fuentesActivas =  new ArrayList<>(fuentes);
  }

  @Override
  public boolean estaConsensuado(Hecho hecho, Fuente fuente) {
    return fuentesActivas
        .stream()
        .allMatch(f -> f.existe(hecho));
  }
}
