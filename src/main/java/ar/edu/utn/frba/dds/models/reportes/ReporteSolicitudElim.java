package ar.edu.utn.frba.dds.models.reportes;

import ar.edu.utn.frba.dds.contracts.Reporte;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
@DiscriminatorValue("Eliminacion")
public class ReporteSolicitudElim extends Reporte {

  @Column(name = "solicitudes_spam")
  private Long solicitudesSpam;

  public ReporteSolicitudElim(Long solicitudesSpam) {
    this.solicitudesSpam = solicitudesSpam;
  }

  @Override
  public String generarCsv() {
    String encabezado = "cantidad_de_solicitues_eliminacion_spam";
    String fila = String.valueOf(this.getSolicitudesSpam());
    return encabezado + "\n" + fila;
  }

}


