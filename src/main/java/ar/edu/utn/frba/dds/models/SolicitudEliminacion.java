package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;

@Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
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

  public Hecho getHecho() {
    return hecho;
  }

  public void modificarEstado(EstadoSolicitudEliminacion nuevoEstado) {
    this.estado = nuevoEstado;
  }

  public boolean esParaElHecho(Hecho hecho) {
    return this.hecho.equals(hecho);
  }

  public String getJustificacion() {
    return justificacion;
  }

  public EstadoSolicitudEliminacion getEstado() {
    return estado;
  }
}
