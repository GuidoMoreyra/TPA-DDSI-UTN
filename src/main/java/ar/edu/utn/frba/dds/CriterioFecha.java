package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.time.LocalDate;
import java.util.Map;

public class CriterioFecha implements Criterio {
  private String fecha;

  public CriterioFecha(String fecha) {
    this.fecha = fecha;
  }

  @Override
  public Boolean cumple(Hecho hecho) {
    return true;
  }

  /*
  En un futuro deberia cambiarse usando localDate
  @Override
  public Boolean seCumpleCriterio(Map<String, String> hecho) {
    return fecha.equals(hecho.get("fecha_Del_Hecho"));  // Comparación directa
  }*/
}


