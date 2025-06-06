package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudEliminacion;

public class SolicitudEliminacion {
  private EstadoSolicitudEliminacion estado;
  private Hecho hecho;
  private String justificacion;
  private int id;

  ///CONSTRUCTOR///

  public SolicitudEliminacion(
       int id,
       Hecho hecho,
       String justificacion
  ) {
    this.hecho = hecho;
    this.id = id;
    if (justificacion.length() < 500) {
      throw new IllegalArgumentException(
        "La justificacion de una solicitud de eliminación no puede ser menor a 500 caracteres"
      );
    }
    this.justificacion = justificacion;
    this.estado = EstadoSolicitudEliminacion.PENDIENTE;
  }

  ///GETTERS///

  public Hecho getHecho() {
    return hecho;
  }

  public String getJustificacion() {
    return justificacion;
  }

  public int getId() {
    return id;
  }

  public EstadoSolicitudEliminacion getEstado() {
    return estado;
  }

  /// METODOS ///
  //Estados Solicitudes
  public void modificarEstado(EstadoSolicitudEliminacion nuevoEstado) {
      this.estado = nuevoEstado;
  }




}
