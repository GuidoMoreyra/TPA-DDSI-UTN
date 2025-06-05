package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import java.time.LocalDate;

public class SolicitudAgregacion {
  private Integer id;
  private EstadoSolicitudAgregacion estado;
  private Hecho hecho;
  private Boolean esAnonimo;
  private LocalDate fechaCreacion;

  //CONSTRUCTOR
  public SolicitudAgregacion(Integer id, Hecho hecho, Boolean esAnonimo) {
    this.id = id;
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

  //GETTERS

  public EstadoSolicitudAgregacion getEstado() {
    return estado;
  }

  public Hecho getHecho() {
    return hecho;
  }

  public Integer getId() {
    return id;
  }

  //METODOS

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

}
