package ar.edu.utn.frba.dds.contracts;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;

public interface Fuente {
  List<Hecho> obtenerHechos();

  public boolean existe(Hecho hecho);

  public Hecho buscar(Hecho hecho);

}
