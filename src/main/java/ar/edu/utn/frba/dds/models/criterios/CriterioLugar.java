package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.models.Hecho;

public record CriterioLugar(String localidad) implements Criterio {

  /// TODO - Aca deberiamos usar la api del mapa para ver si la localidad que
  ///   se paso al criterio  contiene la de la cordenada.
  /// Ej: Argentina contiene a CABA , La Pampa , Cordoba etc.
  /// Por el momento solo los comparo.
  public Boolean cumple(Hecho hecho) {
    //se usa localidad para poder testear mientras no tengamos la API
    return localidad.equals(
        hecho.getCoordenadas().localidad()
    );
  }

}