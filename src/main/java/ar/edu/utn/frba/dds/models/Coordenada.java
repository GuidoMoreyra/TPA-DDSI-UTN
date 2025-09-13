package ar.edu.utn.frba.dds.models;

import javax.persistence.Embeddable;
import lombok.Setter;

@Embeddable
public class Coordenada {
  public double longitud;
  public double latitud;
  @Setter
  public String localidad;

  public Coordenada(double longitud, double latitud) {
    this.longitud = longitud;
    this.latitud = latitud;
    localidad = "Buenos Aires";


  }

  public Coordenada() { }

  public double getLatitud() {
    return latitud;
  }

  public double getLongitud() {
    return longitud;
  }
  
  public String getLocalidad() {
    return localidad;
  }

}
