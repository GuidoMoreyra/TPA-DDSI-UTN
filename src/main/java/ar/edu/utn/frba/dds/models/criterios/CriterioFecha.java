package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.models.Hecho;
import java.time.LocalDate;

public record CriterioFecha(LocalDate fechaInicial, LocalDate fechaFinal) implements Criterio {

  public Boolean cumple(Hecho hecho) {
    return true;
  }

}