package ar.edu.utn.frba.dds.models.criterios;


import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.criterios.Criterio;
import java.time.LocalDate;
import java.util.Map;

public class CriterioFecha implements Criterio {
  private LocalDate fechaInicial;
  private LocalDate fechaFinal;

  public CriterioFecha(LocalDate fechaInicial, LocalDate fechaFinal) {
    this.fechaInicial = fechaInicial;
    this.fechaFinal = fechaFinal;
  }

  public Boolean cumple(){
    return
  }

}