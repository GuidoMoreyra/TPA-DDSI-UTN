package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.models.Hecho;
import java.time.LocalDate;

public class SolicitudAgregacionDto {
  public Boolean esAnonimo;
  public Hecho hecho;

  public SolicitudAgregacionDto(Boolean esAnonimo, Hecho hecho) {
    this.esAnonimo = esAnonimo;
    this.hecho = hecho;
  }
}
