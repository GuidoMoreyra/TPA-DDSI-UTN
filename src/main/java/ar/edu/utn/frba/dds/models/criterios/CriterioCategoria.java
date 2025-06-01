package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.models.Hecho;

public class CriterioCategoria implements Criterio {
  private String categoria;

  public CriterioCategoria(String categoria) {
    this.categoria = categoria;
  }

  public Boolean cumple(Hecho hecho) {
    return this.categoria.equals(hecho.getCategoria());
  }
}


