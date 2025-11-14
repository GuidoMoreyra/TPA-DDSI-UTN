package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.enums.Provincia;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Embeddable
public class Coordenada {

  private double latitud;
  private double longitud;
  @Setter private String localidad = "buenos aires";

  public Coordenada(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public Coordenada() {}

  public Provincia obtenerProvincia() {

    for (Provincia p : Provincia.values()) {
      if (p != Provincia.PROVINCIA_DESCONOCIDA && p.contiene(this.latitud, this.longitud)) {

        return p;
      }
    }

    return Provincia.PROVINCIA_DESCONOCIDA;
  }
}
