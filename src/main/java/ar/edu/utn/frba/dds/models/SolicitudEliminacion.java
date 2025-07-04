package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import lombok.Getter;

@Getter
public final class SolicitudEliminacion {
  private EstadoSolicitudEliminacion estado = EstadoSolicitudEliminacion.PENDIENTE;
  private final Hecho hecho;
  private final String justificacion;

  public SolicitudEliminacion(
       Hecho hecho,
       String justificacion
  ) {
    if (justificacion.length() < 500) {
      throw new IllegalArgumentException(
        "La justificacion de una solicitud de eliminación no puede ser menor a 500 caracteres"
      );
    }

    this.hecho = hecho;
    this.justificacion = justificacion;
  }

  public void modificarEstado(EstadoSolicitudEliminacion nuevoEstado) {
    this.estado = nuevoEstado;
  }

  public boolean esParaElHecho(Hecho hecho) {
    return this.hecho.equals(hecho);
  }

  public EstadoSolicitudEliminacion getEstado() {
    return estado;
  }

  public Hecho getHecho() {
    return hecho;
  }

  public String getJustificacion() {
    return justificacion;
  }
}
