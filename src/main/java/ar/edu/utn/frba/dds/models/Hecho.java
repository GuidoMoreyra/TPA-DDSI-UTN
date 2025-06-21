package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.models.enums.OrigenHecho;
import ar.edu.utn.frba.dds.repositories.SolicitudRepositorySingleton;
import java.time.LocalDate;

public class Hecho {
  private int contadorGlobal;
  private int id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Coordenada coordenadas;
  private LocalDate fechaDelHecho;
  private LocalDate fechaCreacion;
  private OrigenHecho origen;
  private Integer idSolicitudAgregacion;

  ////CONSTRUCTOR///

  public Hecho(String titulo, String descripcion, String categoria,
               double latitud, double longitud, LocalDate fechaDelHecho, OrigenHecho origen,
              Integer idSolicitudAgregacion) {
    this(
        titulo, descripcion, categoria,  latitud,  longitud,
        fechaDelHecho, origen, null, idSolicitudAgregacion
    );
  }

  public Hecho(String titulo, String descripcion, String categoria,
               double latitud, double longitud, LocalDate fechaDelHecho, OrigenHecho origen,
               String contenidoMultimedia, Integer idSolicitudAgregacion) {
    
    this.id = contadorGlobal++;
    this.contenidoMultimedia = contenidoMultimedia;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.coordenadas = new Coordenada(longitud, latitud);
    this.fechaDelHecho = fechaDelHecho;
    this.fechaCreacion = LocalDate.now();
    this.origen = origen;
    this.idSolicitudAgregacion = idSolicitudAgregacion;

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

  public Boolean estaActivo() {
    return SolicitudRepositorySingleton.getInstance()
        .obtenerSolicitudesEliminacionSegunEstado(
            EstadoSolicitudEliminacion.APROBADO
        )
        .stream()
        .noneMatch(
            solicitud -> solicitud.esParaElHecho(this)
        );
  }

  public int getId() {
    return id;
  }

  public String getContenidoMultimedia() {
    return contenidoMultimedia;
  }


  ////METODOS///

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

  public Boolean tieneSugerencias() {
    return SolicitudRepositorySingleton.getInstance()
        .obtenerSolicitudesAgregacionSegunEstado(EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS)
        .stream()
        .anyMatch(s -> s.getHecho().equals(this));
  }


}


