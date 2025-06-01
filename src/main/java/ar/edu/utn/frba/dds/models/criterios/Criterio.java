package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.Map;


public interface Criterio {

  Boolean cumple(Hecho hecho);

}
