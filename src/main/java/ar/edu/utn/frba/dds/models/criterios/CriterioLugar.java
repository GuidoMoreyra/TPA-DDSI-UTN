package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.Map;



public class CriterioLugar implements Criterio {
  private String localidad;

  public CriterioLugar(String localidad) {
    this.localidad = localidad;
  }

  /// Aca deberiamos usar la api del mapa para ver si la localidad que
  ///   se paso al criterio  contiene la de la cordenada.
  /// Ej: Argentina contiene a CABA , La Pampa , Cordoba etc.
  /// Por el momento solo los comparo.
  public Boolean cumple (Hecho hecho) {
    return localidad.equals(hecho.getLugar().localidad);
  }


}