package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.util.ArrayList;
import java.util.List;

public class MayoriaSimple implements AlgoritmoDeConsenso {

  private List<Fuente> fuentesActivas;
  //private List<Hecho> hechosAgregados;
  //private final HechosRepository repositorio = HechosRepository.getInstance();

  public MayoriaSimple(List<Fuente> fuentesActivas) {
    this.fuentesActivas = new ArrayList<>(fuentesActivas);
    //this.hechosAgregados = repositorio.getHechos();

  }

  @Override
  public boolean estaConsensuado(Hecho hecho) {
    long repeticiones = 1;
    /*hechosAgregados.stream()
        .filter(h -> h.getTitulo().equals(hecho.getTitulo()))
        .count();
    */

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
