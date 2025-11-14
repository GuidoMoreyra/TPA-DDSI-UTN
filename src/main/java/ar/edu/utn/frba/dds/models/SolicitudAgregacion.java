package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Solicitud;
import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
@Entity
@Table(name = "solicitudes_agregacion")
@AllArgsConstructor
public final class SolicitudAgregacion implements Solicitud {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado")
  private EstadoSolicitudAgregacion estado = EstadoSolicitudAgregacion.PENDIENTE;

  @ManyToOne
  Usuario usuario; //Si es null es anonimo

  @OneToOne
  private Hecho hecho;


  @Column(name = "fecha_creacion")
  private LocalDate fechaCreacion = LocalDate.now();

  public SolicitudAgregacion() {}

  public SolicitudAgregacion(Hecho hecho, Usuario usaruio) {
    this.hecho = hecho;
    this.usuario = usuario;
  }

  public SolicitudAgregacion(Hecho hecho, Usuario usaruio, LocalDate fechaCreacion) {
    this.hecho = hecho;
    this.usuario = usuario;
    this.fechaCreacion = fechaCreacion;
  }

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
    return usuario != null && fechaCreacion.isAfter(LocalDate.now().minusDays(7));
  }

}
