package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;
import java.util.Map;

public interface Fuente {
  List<Hecho> ObtenerHechos();
}
