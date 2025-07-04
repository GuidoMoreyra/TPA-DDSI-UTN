package ar.edu.utn.frba.dds.contracts;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;

public interface Fuente {
  List<Hecho> obtenerHechos();

  default boolean existe(Hecho hecho) {
    return this
        .obtenerHechos()
        .contains(hecho);
  }

  default Hecho buscar(Hecho hecho) {
    return this
        .obtenerHechos()
        .stream()
        .filter(hecho::compararHecho)
        .findFirst()
        .orElse(null);
  }
}
