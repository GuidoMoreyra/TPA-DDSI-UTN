package ar.edu.utn.frba.dds;

import java.time.LocalDate;

public class Hecho {
  private String titulo;
  private String descripcion;
  private String categoria;
  public String contenidoMultimedia;
  private double latitud;
  private double longitud;
  private LocalDate fechaDelHecho;
  private OrigenHecho origen;

  ////CONSTRUCTOR///

  public Hecho(String titulo, String descripcion, String categoria, double latitud,
               double longitud, LocalDate fechaDelHecho, OrigenHecho origen) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.latitud = latitud;
    this.longitud = longitud;
    this.fechaDelHecho = fechaDelHecho;
    this.origen = origen;
  }

  ////GETTERS///

  public String getCategoria() {
    return categoria;
  }

  public LocalDate getFechaDelHecho() {
    return fechaDelHecho;
  }
}
