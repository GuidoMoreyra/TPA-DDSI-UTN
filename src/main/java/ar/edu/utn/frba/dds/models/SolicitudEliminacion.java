package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
@Entity
public final class SolicitudEliminacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "estado")
  private EstadoSolicitudEliminacion estado = EstadoSolicitudEliminacion.PENDIENTE;
  @OneToOne
  private final Hecho hecho;
  @Column(length = 2000)
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
