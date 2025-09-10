package ar.edu.utn.frba.dds.models.reportes;

import ar.edu.utn.frba.dds.contracts.Reporte;
import ar.edu.utn.frba.dds.dto.SolicitudSpamDto;

public class ReporteSolicitudElim implements Reporte {

  private SolicitudSpamDto solicitudSpam;

  public ReporteSolicitudElim(SolicitudSpamDto solicitudSpamDto) {
    this.solicitudSpam = new SolicitudSpamDto(solicitudSpamDto);
  }

  @Override
  public String generarCsv() {
    String encabezado = "cantidad_de_solicitues_eliminacion_spam";
    String fila = String.valueOf(solicitudSpam.getCantidadDeSolicitudSpam());
    return encabezado + "\n" + fila;
  }

}


