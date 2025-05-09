package ar.edu.utn.frba.dds.hecho.generators.DTO;

import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;

public class DatosCSVDTO extends DatosGeneradorHecho {
  private String fila;

  public DatosCSVDTO(
      String fila
  ) {
    this.fila = fila;
  }

  public String getFila() {
    return fila;
  }
}