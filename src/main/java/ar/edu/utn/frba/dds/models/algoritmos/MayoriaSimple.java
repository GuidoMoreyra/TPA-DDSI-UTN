package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class MayoriaSimple implements AlgoritmoDeConsenso {

  private List<Fuente> fuentesActivas;
  private List<Hecho> hechosAgregados;

  public MayoriaSimple(List<Fuente> fuentesActivas) {
    this.fuentesActivas = new ArrayList<>(fuentesActivas);
    this.hechosAgregados = new ArrayList<>(hechosAgregados);
  }

  @Override
  public boolean estaConsensuado(Hecho hecho, Fuente fuente) {
    long repeticiones = hechosAgregados.stream()
        .filter(h -> h.getTitulo().equals(hecho.getTitulo()))
        .count();

    int totalFuentes = fuentesActivas.size();
    return repeticiones >= Math.ceil(totalFuentes / 2.0);

  }

}
