package ar.edu.utn.frba.dds;

import java.time.LocalDate;

public class Hecho {
  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Double latitud;
  private Double longitud;
  private LocalDate fechaDelHecho;
  private OrigenHecho origen;

  ////CONSTRUCTOR///

  public Hecho(String titulo, String descripcion, String categoria, Double latitud, Double longitud, LocalDate fechaDelHecho, OrigenHecho origen) {
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
