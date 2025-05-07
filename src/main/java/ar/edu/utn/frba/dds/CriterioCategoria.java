package ar.edu.utn.frba.dds;

public class CriterioCategoria implements Criterio {
  private String categoria;

  public CriterioCategoria(String categoria) {
    this.categoria = categoria;
  }

  @Override
  public Boolean cumple(Hecho hecho) {
    return this.categoria.equals(hecho.getCategoria());
  }
}
