package ar.edu.utn.frba.dds.hecho.generators.dto;

import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;

public class DatosApiDto extends DatosGeneradorHecho {
  private Object response;

  public DatosApiDto(
      Object response
  ) {
    this.response = response;
  }

  public Object getResponse() {
    return response;
  }
}
