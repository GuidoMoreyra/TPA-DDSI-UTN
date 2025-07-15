package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;

public class MultiplesMenciones implements AlgoritmoDeConsenso {

  private List<Hecho> hechosAgregados;

  public MultiplesMenciones(List<Fuente> fuentesActivas) {

    this.hechosAgregados = new ArrayList<>(hechosAgregados);
  }

  public boolean estaConsensuado(Hecho hecho, Fuente fuente) {
    long repeticiones = hechosAgregados.stream()
        .filter(h -> h.getTitulo().equals(hecho.getTitulo()))
        .count();

    return repeticiones > 1;
  }
}

