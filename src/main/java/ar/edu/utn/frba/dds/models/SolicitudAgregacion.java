package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;



@Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
@Entity
@Table(name = "solicitudes_agregacion")
@AllArgsConstructor
public final class SolicitudAgregacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado")
  private EstadoSolicitudAgregacion estado = EstadoSolicitudAgregacion.PENDIENTE;

  @OneToOne
  private Hecho hecho;

  @Column(name = "es_anonimo")
  private Boolean esAnonimo;

  @Column(name = "fecha_creacion")
  private LocalDate fechaCreacion = LocalDate.now();

  public SolicitudAgregacion() {}

  public SolicitudAgregacion(Hecho hecho, Boolean esAnonimo) {
    this.hecho = hecho;
    this.esAnonimo = esAnonimo;
  }

  public SolicitudAgregacion(Hecho hecho, Boolean esAnonimo, LocalDate fechaCreacion) {
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

}
