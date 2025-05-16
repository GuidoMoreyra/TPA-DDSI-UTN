package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
//import ar.edu.utn.frba.dds.hecho.dto.HechoLugarDto;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosCsvDto;
import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.time.LocalDate;
/*
public class GeneradorHechoCsv implements GeneradorHecho {
  private final DatosCsvDto datos;

  public GeneradorHechoCsv(
      DatosCsvDto datos
  ) {
    this.datos = datos;
  }

  public Hecho generarHecho() {
    String[] datosSeparados = datos.getFila().split(",");

    if (datosSeparados.length != 5) {
      throw new
          IllegalArgumentException("La fila del CSV no contiene la cantidad correcta de datos");
    }

    String titulo = datosSeparados[0];
    String descripcion = datosSeparados[1];
    String categoria = datosSeparados[2];
    //String lugar = datosSeparados[3];
    LocalDate fechaDelHecho = LocalDate.parse(datosSeparados[4]);

    HechoLugarDto hechoLugar = new HechoLugarDto(0.0, 0.0);

    return
        new Hecho(titulo, descripcion, categoria, hechoLugar, fechaDelHecho, OrigenHecho.ESTATICA);
  }
}*/
