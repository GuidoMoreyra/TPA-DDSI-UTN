package ar.edu.utn.frba.dds.hecho.generators.DTO;

import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;

public class DatosCSVDTO extends DatosGeneradorHecho {
  public String fila;

  public DatosCSVDTO(
      String fila
  ) {
    this.fila = fila;
  }
}