package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.models.Hecho;

public class SolicitudEliminacionDto {
  public String justificacion;
  public Hecho hecho;


  public SolicitudEliminacionDto(String justificacion, Hecho hecho) {

    this.justificacion = justificacion;
    this.hecho = hecho;
  }
}


