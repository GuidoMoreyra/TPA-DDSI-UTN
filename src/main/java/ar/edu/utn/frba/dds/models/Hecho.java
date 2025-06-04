package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.models.enums.OrigenHecho;
import java.time.LocalDate;

public class Hecho {
  private static int contadorGlobal;
  private int id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Coordenada coordenadas;
  private LocalDate fechaDelHecho;
  private LocalDate fechaCreacion;
  private OrigenHecho origen;
  private Boolean estaActivo;
  private Boolean tieneSugerencias;

  ////CONSTRUCTOR///

  public Hecho(String titulo, String descripcion, String categoria,
               double latitud, double longitud, LocalDate fechaDelHecho, OrigenHecho origen) {
    this(titulo, descripcion, categoria,  latitud,  longitud, fechaDelHecho, origen, null);
  }

  public Hecho(String titulo, String descripcion, String categoria,
               double latitud, double longitud, LocalDate fechaDelHecho, OrigenHecho origen,
               String contenidoMultimedia) {
    this.id = contadorGlobal++;
    this.contenidoMultimedia = contenidoMultimedia;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.coordenadas = new Coordenada(longitud, latitud);
    this.fechaDelHecho = fechaDelHecho;
    this.fechaCreacion = LocalDate.now();
    this.origen = origen;
    this.estaActivo =  true; //El hecho por defecto está activo
    this.tieneSugerencias = false; //Por defecto no hay sugerencias sobre el hecho

  }

  ////GETTERS///

  public String getTitulo() {
    return titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getCategoria() {
    return categoria;
  }

  public Coordenada getLugar() {
    return coordenadas;
  }

  public LocalDate getFechaDelHecho() {
    return fechaDelHecho;
  }

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }

  public OrigenHecho getOrigen() {
    return origen;
  }

  public Boolean getEstado() {
    return estaActivo;
  }

  ////METODOS///

  public void desactivar() {
    this.estaActivo = false;
  }

  public void aplicarCambios(CambiosHechoDto cambios) {
    if (cambios.getTitulo() != null) {
      this.titulo = cambios.getTitulo();
    }
    if (cambios.getDescripcion() != null) {
      this.descripcion = cambios.getDescripcion();
    }
    if (cambios.getCategoria() != null) {
      this.categoria = cambios.getCategoria();
    }
    if (cambios.getContenidoMultimedia() != null) {
      this.contenidoMultimedia = cambios.getContenidoMultimedia();
    }
    if (cambios.getCoordenadas() != null) {
      this.coordenadas = cambios.getCoordenadas();
    }
    if (cambios.getOrigen() != null) {
      this.origen = cambios.getOrigen();
    }
  }

}


