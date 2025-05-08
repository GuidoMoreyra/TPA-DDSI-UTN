package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosAPIDTO;
import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class GeneradorHechoAPI implements GeneradorHecho {
  private final DatosAPIDTO datosAPI;

  public GeneradorHechoAPI(
      DatosAPIDTO datosAPI
  ) {
    this.datosAPI = datosAPI;
  }

  public Hecho generarHecho() {
    return null;
  }
}
