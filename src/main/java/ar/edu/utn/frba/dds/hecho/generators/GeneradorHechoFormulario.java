package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosFormularioDTO;
import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class GeneradorHechoFormulario implements GeneradorHecho {
  private final DatosFormularioDTO datosFormulario;

  public GeneradorHechoFormulario(
      DatosFormularioDTO datosFormulario
  ) {
    this.datosFormulario = datosFormulario;
  }

  public Hecho generarHecho() {
    return null;
  }
}
