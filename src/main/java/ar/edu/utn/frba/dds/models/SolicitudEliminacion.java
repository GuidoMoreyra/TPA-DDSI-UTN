package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Getter;

@Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
@Entity
@Table(name = "solicitudes_eliminacion")
public final class SolicitudEliminacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado")
  private EstadoSolicitudEliminacion estado = EstadoSolicitudEliminacion.PENDIENTE;

  @OneToOne
  private Hecho hecho;
  @Column(length = 2000)
  private String justificacion;

  @Getter
  @Column(name = "es_spam")
  private boolean esSpam;

  public SolicitudEliminacion(
       Hecho hecho,
       String justificacion,
       DetectorDeSpamBasico detector
  ) {
    if (justificacion.length() < 500) {
      throw new IllegalArgumentException(
        "La justificacion de una solicitud de eliminación no puede ser menor a 500 caracteres"
      );
    }

    this.hecho = hecho;
    this.justificacion = justificacion;
    this.esSpam = detector.esSpam(justificacion);
  }

  public SolicitudEliminacion() {}

  public void modificarEstado(EstadoSolicitudEliminacion nuevoEstado) {
    this.estado = nuevoEstado;
  }

  public boolean esParaElHecho(Hecho hecho) {
    return this.hecho.equals(hecho);
  }

}
