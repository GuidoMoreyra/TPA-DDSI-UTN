package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;
import java.util.stream.Collectors;

public class MultiplesMenciones implements AlgoritmoDeConsenso {

  private Fuente fuentePrincipal;

  public  MultiplesMenciones(Coleccion coleccion) {
    fuentePrincipal = coleccion.getFuente();
  }

  private boolean distintaFuente(Fuente fuente) {
    return fuente != fuentePrincipal;
  }

  private List<Fuente> fuentesAbuscar(List<Fuente> fuentes) {

    return fuentes.stream()
        .filter(fuenteAbuscar -> distintaFuente(fuenteAbuscar))
        .collect(Collectors.toList());
  }

  @Override
  public boolean estaConsensuado(Hecho hecho, List<Fuente> fuentes) {

    List<Fuente> fuentesSinPrincipal = fuentesAbuscar(fuentes);
    List<Hecho> coincidenciaHecho = this.transformarFuentesahechos(hecho, fuentesSinPrincipal);

    Integer coincidencia = 0;
    coincidencia = this.contarConincidencias(hecho, coincidenciaHecho, coincidencia);

    return coincidencia >= 2;
  }

  private Integer contarConincidencias(Hecho hecho,
                                       List<Hecho> coincidenciaHecho, Integer coincidencia) {
    for (Hecho hechoAux : coincidenciaHecho) {
      if (coincidencia < 2 && this.compararHecho(hecho, hechoAux)) {
        coincidencia++;
      } else if (coincidencia >= 2 && this.compararRigurosa(hecho, hechoAux)) {
        coincidencia++;
      }
    }
    return coincidencia;
  }


  private List<Hecho> transformarFuentesahechos(Hecho hechoBuscado, List<Fuente> fuentes) {
    List<Hecho> hechos =
        fuentes.stream()
            .filter(fuente -> fuente.existe(hechoBuscado))
            .map(fuente -> fuente.buscar(hechoBuscado))
            .collect(Collectors.toList());
    return hechos;
  }

  private boolean compararRigurosa(Hecho hecho, Hecho hechoAcomparar) {
    return hecho.compararRigurosa(hechoAcomparar);
  }

  private boolean compararHecho(Hecho hecho, Hecho hechoAcomparar) {
    return hecho.compararHecho(hechoAcomparar);
  }

}

