package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public final class SolicitudAgregacion {
  private EstadoSolicitudAgregacion estado;
  private final Hecho hecho;
  private final Boolean esAnonimo;
  private final LocalDate fechaCreacion;

  //CONSTRUCTOR
  public SolicitudAgregacion(Hecho hecho, Boolean esAnonimo) {
    this.estado = EstadoSolicitudAgregacion.PENDIENTE;
    this.hecho = hecho;
    this.esAnonimo = esAnonimo;
    this.fechaCreacion = LocalDate.now();
  }

  // Constructor adicional solo para testeo
  public SolicitudAgregacion(Hecho hecho, Boolean esAnonimo, LocalDate fechaCreacion) {
    this.estado = EstadoSolicitudAgregacion.PENDIENTE;
    this.hecho = hecho;
    this.esAnonimo = esAnonimo;
    this.fechaCreacion = fechaCreacion;
  }

  //Estados Solicitudes
  public void aceptarSolicitud() {
    this.estado = EstadoSolicitudAgregacion.ACEPTADO;
  }

  public void aceptarSolicitudConSugerencias(CambiosHechoDto sugerencias) {
    this.hecho.aplicarCambios(sugerencias);
    this.estado = EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS;
  }

  public void rechazarSolicitud() {
    this.estado = EstadoSolicitudAgregacion.RECHAZADO;
  }

  //Otros
  public boolean puedeEditar() {
    return !esAnonimo && fechaCreacion.isAfter(LocalDate.now().minusDays(7));
  }

  public EstadoSolicitudAgregacion getEstado() {
    return estado;
  }

  public Hecho getHecho() {
    return hecho;
  }

  public Boolean getEsAnonimo() {
    return esAnonimo;
  }

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }
}
