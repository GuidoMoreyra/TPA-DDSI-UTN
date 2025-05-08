package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.util.Map;

public class CriterioLatitud implements  Criterio {

  private double latitud;

  public CriterioLatitud(double latitud) {
    this.latitud = latitud;
  }
  @Override
  public Boolean cumple(Hecho hecho) {
    return true;
  }

  @Override

  public Boolean seCumpleCriterio(Map<String, String> hecho) {
    double latitudHecho = Double.parseDouble(hecho.get("latitud"));
    return latitudHecho == latitud;
  }
}
