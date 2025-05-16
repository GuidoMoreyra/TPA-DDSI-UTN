package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CriterioFecha implements Criterio {
  private LocalDate fecha;

  public CriterioFecha(String fecha) {

    this.fecha = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

  @Override
  public Boolean cumple(Hecho hecho) {

    return fecha.equals(hecho.getFechaDelHecho()) ;
  }

  /*
  En un futuro deberia cambiarse usando localDate
  @Override
  public Boolean seCumpleCriterio(Map<String, String> hecho) {
    return fecha.equals(hecho.get("fecha_Del_Hecho"));  // Comparación directa
  }*/
}


