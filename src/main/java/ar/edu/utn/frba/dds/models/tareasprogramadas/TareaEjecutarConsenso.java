package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.contracts.TareaProgramada;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.EjecutarConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;

public class TareaEjecutarConsenso implements TareaProgramada {
  private EjecutarConsenso consensuar;
  private List<Hecho> hechos;

  public TareaEjecutarConsenso(EjecutarConsenso consensuar, List<Hecho> hechos) {
    this.consensuar = consensuar;
    this.hechos = hechos;
  }

  @Override

  public void ejecutar() {
    consensuar.evaluar(hechos);

  }
}
