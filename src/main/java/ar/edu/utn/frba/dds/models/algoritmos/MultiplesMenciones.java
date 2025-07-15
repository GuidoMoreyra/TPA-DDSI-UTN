package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.util.ArrayList;
import java.util.List;

public class MultiplesMenciones implements AlgoritmoDeConsenso {

  private List<Hecho> hechosAgregados;
  private final HechosRepository repositorio = HechosRepository.getInstance();

  public MultiplesMenciones(List<Fuente> fuentesActivas) {

    this.hechosAgregados = repositorio.getHechos();
  }

  public boolean estaConsensuado(Hecho hecho) {
    long repeticiones = hechosAgregados.stream()
        .filter(h -> h.getTitulo().equals(hecho.getTitulo()))
        .count();

    return repeticiones > 1;
  }
}

