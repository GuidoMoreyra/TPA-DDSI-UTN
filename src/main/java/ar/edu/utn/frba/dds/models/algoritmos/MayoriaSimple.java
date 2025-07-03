package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class MayoriaSimple implements AlgoritmoDeConsenso {

  private List<Fuente> fuentesActivas;

  public MayoriaSimple(List<Fuente> fuentes) {
    fuentesActivas =  new ArrayList<>(fuentes);
  }

  @Override
  public boolean estaConsensuado(Hecho hecho, Fuente fuente) {
    long cantidadQueLoConfirman = fuentesActivas
        .stream()
        .filter(f -> f.existe(hecho))
        .count();

    int totalFuentes = fuentesActivas.size();
    return cantidadQueLoConfirman >= Math.ceil(totalFuentes / 2.0);

  }

}
