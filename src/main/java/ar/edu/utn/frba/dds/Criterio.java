package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;


public interface Criterio {

  public Boolean cumple(Hecho hecho);

}
