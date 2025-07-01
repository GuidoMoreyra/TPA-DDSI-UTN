package ar.edu.utn.frba.dds.contracts;

import ar.edu.utn.frba.dds.models.Hecho;

public interface Criterio {

  Boolean cumple(Hecho hecho);

}
