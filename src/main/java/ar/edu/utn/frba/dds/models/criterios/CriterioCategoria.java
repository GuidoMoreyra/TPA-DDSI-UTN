package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.models.Hecho;
import lombok.AllArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Map;

@Entity
@DiscriminatorValue("categoria")
@AllArgsConstructor
public class CriterioCategoria extends Criterio {

  private String categoria;

  public CriterioCategoria() {}

  @Override
  public Boolean cumple(Hecho hecho) {
    return this.categoria.equals(
        hecho.getCategoria()
    );
  }

  public Boolean seCumpleCriterio(Map<String, String> unHecho) {
    return this.categoria.equals(
        unHecho.get("categoria")
    );
  }

}


