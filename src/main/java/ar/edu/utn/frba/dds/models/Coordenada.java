package ar.edu.utn.frba.dds.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

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

  public Coordenada() {}
}
