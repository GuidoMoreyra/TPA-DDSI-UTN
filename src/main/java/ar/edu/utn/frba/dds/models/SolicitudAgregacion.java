package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
@Entity
public final class SolicitudAgregacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado")
  private EstadoSolicitudAgregacion estado;
  @OneToOne
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
