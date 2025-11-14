package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.List;

@SuppressFBWarnings("EI_EXPOSE_REP")
public class EjecutarConsenso {

  // private final HechosRepository repositorio = HechosRepository.getInstance();

  public EjecutarConsenso() {}

  public void aplicarConsensovdos(
      List<Fuente> fuentesActivas, List<AlgoritmoDeConsenso> algoritmos) {
    // recorre cada algoritmo
    algoritmos.forEach(
        algoritmo -> {
          fuentesActivas.stream()
              .flatMap(fuente -> fuente.obtenerHechos().stream())
              // formo una lista de hechos unica
              // filtro los hechos que cumplen con el algoritmo de consenso
              .filter(hecho -> algoritmo.realizarConsenso(hecho, fuentesActivas))
              .forEach(hecho -> hecho.agregarConsenso(algoritmo));
          // dentro de cada hecho que cumple con el consenso le agrego el consenso que cumple.

        });
  }

  /*
  public void evaluarHechos(List<Hecho> hechosNuevos) {
    hechosNuevos.stream()
        //me devuelve una lista de hechos que no se contiene el repo
        .filter((Hecho hecho) -> !repositorio.contiene(hecho))
        //a esos hechos los agrego al repo
        .forEach(repositorio::agregarHecho);
    //a todos los hechos los reproceso aplicandole tanto a viejos como nuevos
    //los algoritmos de consenso
    repositorio.getHechos().forEach(this::aplicarConsensos);
  }*/

  /*
  private void aplicarConsensos(Hecho unHecho) {

    //tengo tanto los hechos nuevos como viejos dentro del repositorio

    List<Hecho> hechosActuales = repositorio.getHechos();

    // me fijo que consensos se cumplen con los nuevos hechos
    List<TipoDeConsenso> consensosActuales = algoritmos.stream()
        //cambio la firma de hechosActuales ahora se lo paso a estaConsensuado
        .filter(algoritmo -> algoritmo.estaConsensuado(unHecho, hechosActuales))
        .map(algoritmo -> algoritmo.getTipo())
        .toList();

    // Reemplazar los consensos anteriores por los consensos nuevos que se cumplen
    unHecho.setAlgoritmos(consensosActuales);

  }*/

  /*
  private List<Hecho> agregarHechos() {
    return fuentesActivas.stream()
        .flatMap(fuente -> fuente.obtenerHechos().stream())
        .collect(Collectors.toList());
  }*/

}
