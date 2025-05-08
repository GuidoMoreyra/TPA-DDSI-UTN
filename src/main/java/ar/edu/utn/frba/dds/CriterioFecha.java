package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.models.Hecho;
import ar.edu.utn.frba.dds.hecho.models.factories.GeneradorHechoFactory;

import java.time.LocalDate;

public class CriterioFecha implements Criterio {
  private final LocalDate desde;
  private final LocalDate hasta;

  public CriterioFecha(LocalDate desde, LocalDate hasta) {
    this.desde = desde;
    this.hasta = hasta;
  }

  @Override
  public Boolean cumple(Hecho hecho) {
    return !hecho.getFechaDelHecho().isBefore(this.desde)
        && !hecho.getFechaDelHecho().isAfter(this.hasta);
  }

  public void generarHecho() {
    GeneradorHecho generadorHecho = GeneradorHechoFactory.crear(OrigenHecho.ESTATICA);
    Hecho hecho = generadorHecho.generarHecho();
  }
}
