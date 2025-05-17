package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;



public class CriterioLugar implements Criterio {
  private Double latitud;
  private Double longitud;
  private String tipoLugar;

  public CriterioLugar(Double latitud, Double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public CriterioLugar(String tipoLugar) {
    this.tipoLugar = tipoLugar;
  }

  @Override
  public Boolean cumple(Hecho hecho) {
    return true;
    //TODO - falta API que devuelva una localidad
  }

  public Double getLatitud() {
    return latitud;
  }

  public Double getLongitud() {
    return longitud;
  }

  public String getTipoLugar() {
    return tipoLugar;
  }
}
