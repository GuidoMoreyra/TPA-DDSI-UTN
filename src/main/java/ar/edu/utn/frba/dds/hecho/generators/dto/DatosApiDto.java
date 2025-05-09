package ar.edu.utn.frba.dds.hecho.generators.dto;

import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;

public class DatosApiDto extends DatosGeneradorHecho {
  public Object response;

  public DatosApiDto(
      Object response
  ) {
    this.response = response;
  }
}
