package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.util.Map;

public interface Fuente {
  Iterable<Hecho> obtenerHechos();
}
