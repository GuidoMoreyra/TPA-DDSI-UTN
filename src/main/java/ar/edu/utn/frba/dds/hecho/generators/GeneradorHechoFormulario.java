package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.DTO.HechoLugarDTO;
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
    HechoLugarDTO lugarDTO = this.convertirLugarACoordenadas(datosFormulario.lugar);

    return new Hecho(
      datosFormulario.titulo,
      datosFormulario.descripcion,
      datosFormulario.categoria,
      lugarDTO,
      datosFormulario.fechaAcontecimiento,
      datosFormulario.origenHecho
    );
  }

  private HechoLugarDTO convertirLugarACoordenadas(String lugar) {
    return new HechoLugarDTO(0.0, 0.0);
  }
}
