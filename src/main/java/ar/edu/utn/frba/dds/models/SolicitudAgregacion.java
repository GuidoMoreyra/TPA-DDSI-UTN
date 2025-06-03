package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;

public class SolicitudAgregacion {
  private EstadoSolicitudAgregacion estado;
  private Hecho hecho;

  //CONSTRUCTOR
  public SolicitudAgregacion(Hecho hecho) {
    this.estado = EstadoSolicitudAgregacion.PENDIENTE;
    this.hecho = hecho;
  }

  //GETTERS

  public EstadoSolicitudAgregacion getEstado() {
    return estado;
  }

  public Hecho getHecho() {
    return hecho;
  }

  //METODOS
  public void aceptarSolicitud() {
    this.estado = EstadoSolicitudAgregacion.ACEPTADO;
  }

  public void aceptarSolicitudConSugerencias() {
    this.estado = EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS;
  }

  public void rechazarSolicitud() {
    this.estado = EstadoSolicitudAgregacion.RECHAZADO;
  }
}
