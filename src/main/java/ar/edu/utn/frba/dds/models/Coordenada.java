package ar.edu.utn.frba.dds.models;

public class Coordenada {
  public final double longitud;
  public final double latitud;
  public final String localidad;

  public Coordenada(double longitud, double latitud) {
    this.longitud = longitud;
    this.latitud = latitud;

    /// TODO - Aca la estoy asignando manualmente
    ///  pero la idea seria que se utilize alguna api para obtenerla
    localidad = "Buenos Aires";
  }

  public String getLocalidad() {
    return localidad;
  }

  public double getLongitud() {
    return longitud;
  }

  public double getLatitud() {
    return latitud;
  }
}
