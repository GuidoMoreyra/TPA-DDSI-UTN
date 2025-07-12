package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.algoritmos.ConsensoAbsoluto;
import ar.edu.utn.frba.dds.models.algoritmos.MayoriaSimple;
import ar.edu.utn.frba.dds.models.algoritmos.MultiplesMenciones;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EjecutarConsenso {
  private final List<AlgoritmoDeConsenso> algoritmos;
  private final List<Fuente> fuentesActivas;
  private final List<Hecho> hechosMezclados;
  private final HechosRepository repositorio = HechosRepository.getInstance();

  public EjecutarConsenso(List<Fuente> fuentesActivas) {
    this.fuentesActivas = new ArrayList<>(fuentesActivas);
    this.hechosMezclados = new ArrayList<>(this.agregarHechos());


    //Aca da error por que todavia no se cambiaron los algoritmos
    //                               para utilizar el repositorio
    this.algoritmos = List.of(
        new ConsensoAbsoluto(fuentesActivas),
        new MayoriaSimple(fuentesActivas),
        new MultiplesMenciones()
    );
  }

  public void evaluar() {
    for (Hecho hechoIndex : hechosMezclados) {
      if(!repositorio.contiene(hechoIndex)) {
        repositorio.agregarHecho(hechoIndex);
        for(Hecho hechoDelRepositorio : repositorio.getHechos()) {
          for (AlgoritmoDeConsenso algoritmo : algoritmos) {
            if (algoritmo.estaConsensuado(hechoIndex, null)) { // fuente no se usa
              hechoIndex.agregarConsenso(mapearTipo(algoritmo));
            }
          }
        }
      }
    }
  }

  private TipoDeConsenso mapearTipo(AlgoritmoDeConsenso algoritmo) {
    if (algoritmo instanceof ConsensoAbsoluto) {
      return TipoDeConsenso.CONSENSO_ABSOLUTO;
    }
    if (algoritmo instanceof MayoriaSimple) {
      return TipoDeConsenso.MAYORIA_SIMPLE;
    }
    if (algoritmo instanceof MultiplesMenciones) {
      return TipoDeConsenso.MULTIPLES_MENCIONES;
    }
    throw new IllegalArgumentException("Algoritmo desconocido");
  }

  private List<Hecho> agregarHechos() {
    return fuentesActivas.stream()
        .flatMap(fuente -> fuente.obtenerHechos().stream())
        .collect(Collectors.toList());
  }



}
