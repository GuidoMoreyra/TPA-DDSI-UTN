package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.models.Hecho;

public class SolicitudEliminacionDto {
  public String justificacion;
  public Hecho hecho;

  public void setJustificacion(String justificacion) {
    this.justificacion = justificacion;
  }

  public void setHecho(Hecho hecho) {
    this.hecho = hecho;
  }
}


