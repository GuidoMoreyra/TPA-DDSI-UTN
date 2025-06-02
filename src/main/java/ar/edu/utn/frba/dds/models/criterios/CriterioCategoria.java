package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.Map;

public class CriterioCategoria implements Criterio {
  private String categoria;

  public CriterioCategoria(String categoria) {
    this.categoria = categoria;
  }

  @Override
  public Boolean cumple(Hecho hecho) {
    return this.categoria.equals(hecho.getCategoria());
  }

  public Boolean seCumpleCriterio(Map<String, String> unHecho) {
    return this.categoria.equals(unHecho.get("categoria"));
  }

}


