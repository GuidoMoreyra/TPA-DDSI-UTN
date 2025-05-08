package ar.edu.utn.frba.dds;

import java.time.LocalDate;
import java.util.Map;

public class CriterioFecha implements Criterio{
  private LocalDate desde;
  private LocalDate hasta;

  public CriterioFecha(LocalDate desde, LocalDate hasta) {
    this.desde = desde;
    this.hasta = hasta;
  }

  @Override
  public Boolean cumple(Hecho hecho) {
    return !hecho.getFechaDelHecho().isBefore(this.desde) &&
        !hecho.getFechaDelHecho().isAfter(this.hasta) ;
  }

  @Override
  public Boolean seCumpleCriterio(Map<String, String> unHecho) {
    return null;
  }
}
