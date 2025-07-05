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

public class EjecutarConsenso {
  private final List<AlgoritmoDeConsenso> algoritmos;
  private final List<Fuente> fuentesActivas;
  private final List<Hecho> hechosAgregados;

  public EjecutarConsenso(List<Fuente> fuentesActivas) {
    this.fuentesActivas = new ArrayList<>(fuentesActivas);
    this.hechosAgregados = new ArrayList<>();

    this.algoritmos = List.of(
        new ConsensoAbsoluto(fuentesActivas, hechosAgregados),
        new MayoriaSimple(fuentesActivas, hechosAgregados),
        new MultiplesMenciones(hechosAgregados)
    );
  }

  public void evaluar(List<Hecho> hechosRepetidos) {
    for (Hecho hecho : hechosRepetidos) {
      for (AlgoritmoDeConsenso algoritmo : algoritmos) {
        if (algoritmo.estaConsensuado(hecho, null)) { // fuente no se usa
          hecho.agregarConsenso(mapearTipo(algoritmo));
        }
      }
      hechosAgregados.add(hecho); // actualizar lista compartida
      HechosRepository.getInstance().agregarHecho(hecho);
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


}
