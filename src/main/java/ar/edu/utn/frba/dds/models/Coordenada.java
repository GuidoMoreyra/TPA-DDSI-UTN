package ar.edu.utn.frba.dds.models;

public class Coordenada {
  public final double longitud;
  public final double latitud;
  public final String localidad;

  public Coordenada(double longitud, double latitud) {
    this.longitud = longitud;
    this.latitud = latitud;

    /// Aca la estoy asignando manualmente
    ///  pero la idea seria que se utilize alguna api para obtenerla
    localidad = "Argentina";
  }
}
