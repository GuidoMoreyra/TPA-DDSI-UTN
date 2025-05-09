package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosApiDto;
import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class GeneradorHechoApi implements GeneradorHecho {
  private final DatosApiDto datosApi;

  public GeneradorHechoApi(DatosApiDto datosApi) {
    this.datosApi = datosApi;
  }

  public Hecho generarHecho() {
    return null;
  }
}
