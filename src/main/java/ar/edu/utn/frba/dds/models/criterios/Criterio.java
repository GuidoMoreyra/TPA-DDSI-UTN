package ar.edu.utn.frba.dds.models.criterios;

import ar.edu.utn.frba.dds.models.Hecho;


public interface Criterio {

  Boolean cumple(Hecho hecho);

}
