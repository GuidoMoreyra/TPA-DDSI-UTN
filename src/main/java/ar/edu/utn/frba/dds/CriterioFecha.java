package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;

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
}
