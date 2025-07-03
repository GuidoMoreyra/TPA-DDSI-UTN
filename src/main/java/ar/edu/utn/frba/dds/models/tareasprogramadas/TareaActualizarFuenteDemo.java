package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.contracts.TareaProgramada;
import ar.edu.utn.frba.dds.repositories.fuentes.AdaptadorFuenteDemo;

public class TareaActualizarFuenteDemo implements TareaProgramada {

  private final AdaptadorFuenteDemo fuente;

  public TareaActualizarFuenteDemo(AdaptadorFuenteDemo fuente) {
    this.fuente = fuente;
  }

  @Override
  public void ejecutar() {
    fuente.actualizar();
  }
}
