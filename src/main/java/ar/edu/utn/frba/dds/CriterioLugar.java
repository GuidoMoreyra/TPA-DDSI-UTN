package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.util.Map;



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

  @Override
  public Boolean seCumpleCriterio(Map<String, String> unHecho) {
    return this.tipoLugar.equals(unHecho.get("tipo_lugar"));
  }
}
