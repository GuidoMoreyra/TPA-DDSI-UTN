package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.util.ArrayList;
import java.util.List;

public class MultiplesMenciones implements AlgoritmoDeConsenso {

  //private List<Hecho> hechosAgregados;


  public MultiplesMenciones(List<Fuente> fuentesActivas) {

  }

  @Override
  public boolean estaConsensuado(Hecho hecho) {
    long repeticiones = 2;

    /*hechosAgregados.stream()
        .filter(h -> h.getTitulo().equals(hecho.getTitulo()))
        .count();
    */

    return repeticiones > 1;
  }


  @Override
  public  boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio) {
    long repeticiones = hechosRepositorio.stream()
        .filter(hechoRepo -> hechoRepo.compararHecho(hecho))
        .count();

    return repeticiones > 1;
  }

}

