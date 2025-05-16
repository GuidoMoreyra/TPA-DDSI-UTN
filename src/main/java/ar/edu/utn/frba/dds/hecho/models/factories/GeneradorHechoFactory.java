package ar.edu.utn.frba.dds.hecho.models.factories;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoApi;
//import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoCsv;
//import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoFormulario;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosApiDto;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosCsvDto;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosFormularioDto;
import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;
import ar.edu.utn.frba.dds.hecho.models.Hecho;
/*
public class GeneradorHechoFactory {
  public static GeneradorHecho crear(OrigenHecho origen, DatosGeneradorHecho datos) {
    return switch (origen) {
      case ESTATICA -> new GeneradorHechoCsv((DatosCsvDto) datos);
      case DINAMICA -> new GeneradorHechoFormulario((DatosFormularioDto) datos);
      case INTERMEDIA -> new GeneradorHechoApi((DatosApiDto) datos);
    };
  }

  public Hecho generarHecho(GeneradorHecho generador) {
    return generador.generarHecho();
  }
}*/