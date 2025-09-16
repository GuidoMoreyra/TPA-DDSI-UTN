package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.algoritmos.ConsensoAbsoluto;
import ar.edu.utn.frba.dds.models.algoritmos.MayoriaSimple;
import ar.edu.utn.frba.dds.models.algoritmos.MultiplesMenciones;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SuppressFBWarnings("EI_EXPOSE_REP")
public class EjecutarConsenso {
  private final List<AlgoritmoDeConsenso> algoritmos;
  private final List<Fuente> fuentesActivas;
  private final List<Hecho> hechosMezclados;
  private final HechosRepository repositorio = HechosRepository.getInstance();


  public EjecutarConsenso(List<Fuente> fuentesActivas, List<AlgoritmoDeConsenso> algoritmos) {
    this.fuentesActivas = new ArrayList<>(fuentesActivas);
    this.hechosMezclados = new ArrayList<>(this.agregarHechos());


    //Aca da error por que todavia no se cambiaron los algoritmos
    //                               para utilizar el repositorio
    this.algoritmos = algoritmos;
  }

  public void evaluarVersionDos() {
    hechosMezclados.stream()
        //me devuelve una lista de hechos que no se contiene el repo
        .filter((Hecho hecho) -> !repositorio.contiene(hecho))
        //a esos hechos los agrego al repo
        .forEach(repositorio::agregarHecho);
    //a todos los hechos los reproceso aplicandole tanto a viejos como nuevos
    //los algoritmos de consenso
    repositorio.getHechos().forEach(this::aplicarConsensos);
  }

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

  }

  private List<Hecho> agregarHechos() {
    return fuentesActivas.stream()
        .flatMap(fuente -> fuente.obtenerHechos().stream())
        .collect(Collectors.toList());
  }



}
