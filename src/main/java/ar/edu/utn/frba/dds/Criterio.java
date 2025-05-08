package ar.edu.utn.frba.dds;

import java.util.Map;

public interface Criterio {

  public Boolean cumple(Hecho hecho);
  public Boolean seCumpleCriterio(Map<String, String> unHecho);
}
