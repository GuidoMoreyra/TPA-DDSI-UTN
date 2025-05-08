package ar.edu.utn.frba.dds.hecho.models;

import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;

import java.time.LocalDate;

public class Hecho {
  private String titulo;
  private String descripcion;
  private String categoria;
  public String contenidoMultimedia;
  private Double latitud;
  private Double longitud;
  private LocalDate fechaDelHecho;
  private OrigenHecho origen;
  private EstadoDelHecho estado;

  ////CONSTRUCTOR///

  public Hecho(String titulo, String descripcion, String categoria, Double latitud,
               Double longitud, LocalDate fechaDelHecho, OrigenHecho origen) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.latitud = latitud;
    this.longitud = longitud;
    this.fechaDelHecho = fechaDelHecho;
    this.origen = origen;
    this.estado =  EstadoDelHecho.ACTIVO; //el hecho por defecto está activo
  }

  ////GETTERS///

  public String getCategoria() {
    return categoria;
  }

  public LocalDate getFechaDelHecho() {
    return fechaDelHecho;
  }

  ////METODOS///

  public void desactivar() {
    this.estado = EstadoDelHecho.INACTIVO;
  }


}


