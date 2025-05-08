package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosCSVDTO;
import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class GeneradorHechoCSV implements GeneradorHecho {
  private final DatosCSVDTO datos;

  public GeneradorHechoCSV(
      DatosCSVDTO datos
  ) {
    this.datos = datos;
  }

  public Hecho generarHecho() {
    return null;
  }
}
