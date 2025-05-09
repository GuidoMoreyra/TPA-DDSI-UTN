package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.dto.HechoLugarDto;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosFormularioDto;
import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class GeneradorHechoFormulario implements GeneradorHecho {
  private final DatosFormularioDto datosFormulario;

  public GeneradorHechoFormulario(
      DatosFormularioDto datosFormulario
  ) {
    this.datosFormulario = datosFormulario;
  }

  public Hecho generarHecho() {
    HechoLugarDto lugarDto = this.convertirLugarCoordenadas(datosFormulario.lugar);

    return new Hecho(
      datosFormulario.titulo,
      datosFormulario.descripcion,
      datosFormulario.categoria,
      lugarDto,
      datosFormulario.fechaAcontecimiento,
      datosFormulario.origenHecho
    );
  }

  private HechoLugarDto convertirLugarCoordenadas(String lugar) {
    return new HechoLugarDto(0.0, 0.0);
  }
}
