package ar.edu.utn.frba.dds;

import java.util.Map;

import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class CriterioLugar implements Criterio{
  private Double latitud;
  private Double longitud;
  private String tipo_lugar;

  public CriterioLugar(Double latitud, Double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public CriterioLugar(String tipoLugar) {
    this.tipo_lugar = tipoLugar;
  }

  @Override
  public Boolean cumple(Hecho hecho) {
    return null;
    //TODO - falta API que devuelva una localidad
  }

  @Override
  public Boolean seCumpleCriterio(Map<String, String> unHecho) {
    return this.tipo_lugar.equals(unHecho.get("tipo_lugar"));
  }
}
