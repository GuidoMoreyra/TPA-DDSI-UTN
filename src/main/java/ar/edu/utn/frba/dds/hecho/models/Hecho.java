package ar.edu.utn.frba.dds.hecho.models;

import ar.edu.utn.frba.dds.EstadoDelHecho;
import ar.edu.utn.frba.dds.hecho.DTO.HechoLugarDTO;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;

import java.time.LocalDate;

public class Hecho {
  private String titulo;
  private String descripcion;
  private String categoria;
  public String contenidoMultimedia;
  private HechoLugarDTO lugar;
  private LocalDate fechaDelHecho;
  private LocalDate fechaCreacion;
  private OrigenHecho origen;
  private EstadoDelHecho estado;

  ////CONSTRUCTOR///

  public Hecho(
          String titulo,
          String descripcion,
          String categoria,
          HechoLugarDTO lugar,
          LocalDate fechaDelHecho,
          OrigenHecho origen
  ) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.lugar = lugar;
    this.fechaDelHecho = fechaDelHecho;
    this.fechaCreacion = LocalDate.now();
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


