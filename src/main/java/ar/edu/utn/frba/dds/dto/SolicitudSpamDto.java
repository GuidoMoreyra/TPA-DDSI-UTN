package ar.edu.utn.frba.dds.dto;

public class SolicitudSpamDto {

  private Long cantidadDeSolicitudSpam;

  public Long getCantidadDeSolicitudSpam() {
    return cantidadDeSolicitudSpam;
  }

  public void setCantidadDeSolicitudSpam(Long cantidadDeSolicitudSpam) {
    this.cantidadDeSolicitudSpam = cantidadDeSolicitudSpam;
  }

  public SolicitudSpamDto(SolicitudSpamDto solicitudSpamDto) {
    this.cantidadDeSolicitudSpam = solicitudSpamDto.getCantidadDeSolicitudSpam();
  }

  public SolicitudSpamDto() {}

}
