package ar.edu.utn.frba.dds.hecho.models.factories;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoAPI;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoCSV;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoFormulario;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosAPIDTO;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosCSVDTO;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosFormularioDTO;
import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;
import ar.edu.utn.frba.dds.hecho.models.Hecho;

public class GeneradorHechoFactory {
  public static GeneradorHecho crear(OrigenHecho origen, DatosGeneradorHecho datos) {
    return switch (origen) {
      case ESTATICA -> new GeneradorHechoCSV((DatosCSVDTO) datos);
      case DINAMICA -> new GeneradorHechoFormulario((DatosFormularioDTO) datos);
      case INTERMEDIA -> new GeneradorHechoAPI((DatosAPIDTO) datos);
    };
  }

  public Hecho generarHecho(GeneradorHecho generador) {
    return generador.generarHecho();
  }
}