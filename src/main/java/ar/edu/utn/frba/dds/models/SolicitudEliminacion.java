package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Getter
@SuppressFBWarnings("EI_EXPOSE_REP")
@Entity
public class SolicitudEliminacion {

  @Id
  @GeneratedValue
  private Long id;

  @Transient
  private EstadoSolicitudEliminacion estado = EstadoSolicitudEliminacion.PENDIENTE;

  @OneToOne
  private Hecho hecho;
  private String justificacion;

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

  public SolicitudEliminacion() {}

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
