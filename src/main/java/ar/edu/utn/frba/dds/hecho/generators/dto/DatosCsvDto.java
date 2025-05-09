package ar.edu.utn.frba.dds.hecho.generators.dto;

import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;

public class DatosCsvDto extends DatosGeneradorHecho {
  private String fila;

  public DatosCsvDto(
      String fila
  ) {
    this.fila = fila;
  }

  public String getFila() {
    return fila;
  }
}