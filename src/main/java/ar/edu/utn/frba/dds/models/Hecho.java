package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Hecho {
  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private final Coordenada coordenadas;
  private final LocalDate fechaDelHecho;
  private final LocalDate fechaCreacion = LocalDate.now();
  @Setter
  private OrigenHecho origen;

  public Hecho(
      String titulo,
      String descripcion,
      String categoria,
      double latitud,
      double longitud,
      LocalDate fechaDelHecho,
      OrigenHecho origen,
      String contenidoMultimedia
  ) {
    this.contenidoMultimedia = contenidoMultimedia;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.coordenadas = new Coordenada(longitud, latitud, "Buenos Aires"); // TODO: cambiar luego
    this.fechaDelHecho = fechaDelHecho;
    this.origen = origen;
  }

  public Boolean estaActivo() {
    return SolicitudesEliminacionRepository
        .getInstance()
        .obtenerSolicitudesConEstado(EstadoSolicitudEliminacion.APROBADO)
        .stream()
        .noneMatch(solicitud -> solicitud.esParaElHecho(this));
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
    //    if (cambios.getCoordenadas() != null) {
    //      this.coordenadas = cambios.getCoordenadas();
    //    }
    if (cambios.getOrigen() != null) {
      this.origen = cambios.getOrigen();
    }
  }

  public Boolean tieneSugerencias() {
    return SolicitudesAgregacionRepository
        .getInstance()
        .obtenerSolicitudesConEstado(EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS)
        .stream()
        .anyMatch(s -> s.getHecho().equals(this));
  }

  public boolean compararRigurosa(Hecho hechoCompar) {
    return this.getTitulo().equals(hechoCompar.getTitulo())
        && this.getDescripcion().equals(hechoCompar.getDescripcion())
        && this.getCategoria().equals(hechoCompar.getCategoria())
        && this.getCoordenadas().equals(hechoCompar.getCoordenadas())
        && this.getFechaDelHecho() == hechoCompar.getFechaDelHecho();
  }



  public boolean compararHecho(Hecho h) {
    return this.getTitulo().equals(h.getTitulo());
  }

}