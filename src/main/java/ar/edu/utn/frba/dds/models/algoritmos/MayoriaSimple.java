package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class MayoriaSimple implements AlgoritmoDeConsenso {

  private final List<Fuente> fuentesActivas;

  public MayoriaSimple(List<Fuente> fuentesActivas) {
    this.fuentesActivas = new ArrayList<>(fuentesActivas);
  }

  @Override
  public TipoDeConsenso getTipo() {
    return TipoDeConsenso.MAYORIA_SIMPLE;
  }

  @Override
  public boolean estaConsensuado(Hecho hecho) {
    long repeticiones = 1;

    int totalFuentes = fuentesActivas.size();
    return repeticiones >= Math.ceil(totalFuentes / 2.0);

  }

  @Override
  public boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio) {
    long  repeticiones = hechosRepositorio.stream()
        .filter(hechoRepo -> hechoRepo.compararHecho(hecho))
        .count();

    int totalFuentes = fuentesActivas.size();
    return repeticiones >= Math.ceil(totalFuentes / 2.0);
  }

}
