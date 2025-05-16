package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.util.Map;


public interface Criterio {

  public Boolean cumple(Hecho hecho);

  //public Boolean seCumpleCriterio(Hecho unHecho);
}
