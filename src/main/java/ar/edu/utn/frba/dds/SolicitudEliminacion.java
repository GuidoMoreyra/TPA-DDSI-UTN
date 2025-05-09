package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import ar.edu.utn.frba.dds.usuario.contracts.GestorHechos;

public class SolicitudEliminacion {
  private EstadoSolicitudEliminacion estado;
  private Hecho hecho;
  private GestorHechos gestor;
  private String justificacion;

  ///CONSTRUCTOR///

  public SolicitudEliminacion(
       Hecho hecho,
       GestorHechos gestor,
       String justificacion
  ) {
    this.estado = EstadoSolicitudEliminacion.PENDIENTE;
    this.hecho = hecho;
    this.gestor = gestor;

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

  public GestorHechos getSolicitante() {
    return gestor;
  }

  public String getJustificacion() {
    return justificacion;
  }

  public EstadoSolicitudEliminacion getEstado() {
    return estado;
  }
}
