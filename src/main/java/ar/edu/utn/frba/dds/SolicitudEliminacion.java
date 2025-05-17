package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class SolicitudEliminacion {
  private EstadoSolicitudEliminacion estado;
  private Hecho hecho;
  private String justificacion;

  ///CONSTRUCTOR///

  public SolicitudEliminacion(
       Hecho hecho,
       String justificacion
  ) {
    this.estado = EstadoSolicitudEliminacion.PENDIENTE;
    this.hecho = hecho;

    if (justificacion.length() < 500) {
      throw new IllegalArgumentException(
        "La justificacion de una solicitud de eliminación no puede ser menor a 500 caracteres"
      );
    }

    this.justificacion = justificacion;
  }

  ///METODOS///

  public void aprobar() {
    this.estado = EstadoSolicitudEliminacion.APROBADO;
  }

  public void rechazar() {
    this.estado = EstadoSolicitudEliminacion.RECHAZADO;
  }

  ///GETTERS///

  public Hecho getHecho() {
    return hecho;
  }

  public String getJustificacion() {
    return justificacion;
  }

  public EstadoSolicitudEliminacion getEstado() {
    return estado;
  }
}
