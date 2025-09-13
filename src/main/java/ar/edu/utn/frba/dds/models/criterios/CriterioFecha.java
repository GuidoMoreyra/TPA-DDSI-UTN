package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.models.Hecho;
import java.time.LocalDate;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;



@Entity
@DiscriminatorValue("CriterioFecha")
public class CriterioFecha extends Criterio {
  private LocalDate fechaInicial;
  private LocalDate fechaFinal;

  public CriterioFecha(LocalDate fechaInicial, LocalDate fechaFinal) {
    this.fechaInicial = fechaInicial;
    this.fechaFinal = fechaFinal;
  }

  public Boolean cumple(Hecho hecho) {

    return (!hecho.getFechaDelHecho().isBefore(fechaInicial)
        && !hecho.getFechaDelHecho().isAfter(fechaFinal));
  }

}