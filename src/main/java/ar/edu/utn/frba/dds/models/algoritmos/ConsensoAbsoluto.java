package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class ConsensoAbsoluto implements AlgoritmoDeConsenso {

  private final List<Fuente> fuentesActivas;

  public ConsensoAbsoluto(List<Fuente> fuentesActivas) {
    this.fuentesActivas = new ArrayList<>(fuentesActivas);
  }

  @Override
  public TipoDeConsenso getTipo() {
    return TipoDeConsenso.CONSENSO_ABSOLUTO;
  }

  @Override
  public boolean realizarConsenso(Hecho hecho, List<Fuente> fuentesActivas) {
    long repeticiones = fuentesActivas.stream()
        .filter(fuente -> fuente.obtenerHechos().stream()
            .anyMatch(hechoDeunafuente -> hecho.compararHecho(hechoDeunafuente)))
        .count();

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
