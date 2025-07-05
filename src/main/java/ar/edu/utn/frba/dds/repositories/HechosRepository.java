package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HechosRepository {
  private static final HechosRepository INSTANCE = new HechosRepository();
  private final List<Hecho> hechos = new ArrayList<>();

  private HechosRepository() {}

  public static HechosRepository getInstance() {
    return INSTANCE;
  }

  public List<Hecho> getHechos() {
    return Collections.unmodifiableList(hechos);
  }

  public void agregarHecho(Hecho hecho) {
    hechos.add(hecho);
  }

  public void limpiar() { //para testear
    this.hechos.clear();
  }
}
