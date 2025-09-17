package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.models.Hecho;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;



@Entity
@DiscriminatorValue("fecha")
@AllArgsConstructor
public class CriterioFecha extends Criterio {

  @Column(name = "fecha_inicial")
  private LocalDate fechaInicial;

  @Column(name = "fecha_final")
  private LocalDate fechaFinal;

  public CriterioFecha() {}

  public Boolean cumple(Hecho hecho) {

    return (!hecho.getFechaDelHecho().isBefore(fechaInicial)
        && !hecho.getFechaDelHecho().isAfter(fechaFinal));
  }

}