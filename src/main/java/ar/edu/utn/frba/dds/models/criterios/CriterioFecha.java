package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.models.Hecho;
import java.time.LocalDate;

public class CriterioFecha implements Criterio {
  private LocalDate fechaInicial;
  private LocalDate fechaFinal;

  public CriterioFecha(LocalDate fechaInicial, LocalDate fechaFinal) {
    this.fechaInicial = fechaInicial;
    this.fechaFinal = fechaFinal;
  }

  public Boolean cumple(Hecho hecho) {
    return true;
  }

  public LocalDate getFechaInicial() {
    return fechaInicial;
  }

  public LocalDate getFechaFinal() {
    return fechaFinal;
  }
}