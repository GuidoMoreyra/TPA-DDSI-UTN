package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class CriterioLugar implements Criterio{
  private Double latitud;
  private Double longitud;

  public CriterioLugar(Double latitud, Double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  @Override
  public Boolean cumple(Hecho hecho) {
    return null;
    //TODO - falta API que devuelva una localidad
  }
}
