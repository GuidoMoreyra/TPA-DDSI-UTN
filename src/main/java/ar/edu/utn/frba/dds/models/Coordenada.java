package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.enums.Provincia;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter
@Embeddable
public class Coordenada {
  public double longitud;
  public double latitud;
  @Setter
  public String localidad = "Buenos Aires";

  public Coordenada(double longitud, double latitud) {
    this.longitud = longitud;
    this.latitud = latitud;
  }

  public Coordenada() { }

  public Provincia obtenerProvincia() {
    for (Provincia p : Provincia.values()) {
      if (p != Provincia.PROVINCIA_DESCONOCIDA
          && p.contiene(this.getLatitud(), this.getLongitud())) {
        return p;
      }
    }
    return Provincia.PROVINCIA_DESCONOCIDA;

  }



}
