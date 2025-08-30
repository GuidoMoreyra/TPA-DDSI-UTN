package ar.edu.utn.frba.dds.models;


import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Coordenada {

  @Id
  @GeneratedValue
  private Long id;

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
