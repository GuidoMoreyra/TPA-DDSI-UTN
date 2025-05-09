package ar.edu.utn.frba.dds.hecho.dto;

public class HechoLugarDto {
  public Double latitud;
  public Double longitud;

  public HechoLugarDto(
      Double latitud,
      Double longitud
  ) {
    this.latitud = latitud;
    this.longitud = longitud;
  }
}
