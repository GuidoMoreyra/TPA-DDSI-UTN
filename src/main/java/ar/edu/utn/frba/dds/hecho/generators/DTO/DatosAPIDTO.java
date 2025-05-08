package ar.edu.utn.frba.dds.hecho.generators.DTO;

import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;

public class DatosAPIDTO extends DatosGeneradorHecho {
  public Object response;

  public DatosAPIDTO(
      Object response
  ) {
    this.response = response;
  }
}
