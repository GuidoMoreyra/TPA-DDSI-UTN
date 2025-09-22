package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;

public class MultiplesMenciones implements AlgoritmoDeConsenso {

  @Override
  public TipoDeConsenso getTipo() {
    return TipoDeConsenso.MULTIPLES_MENCIONES;
  }


  @Override
  public  boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio) {
    long repeticiones = hechosRepositorio.stream()
        .filter(hechoRepo -> hechoRepo.compararHecho(hecho))
        .count();

    return repeticiones > 1;
  }

}

