package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class HechosRepository implements WithSimplePersistenceUnit {
  private static final HechosRepository INSTANCE = new HechosRepository();
  private final List<Hecho> hechos = new ArrayList<>();

  private HechosRepository() {}

  public static HechosRepository getInstance() {
    return INSTANCE;
  }

  @SuppressWarnings("unchecked")
  public List<Hecho> getHechos() {
    return entityManager()
        .createQuery("from Hecho", Hecho.class)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public void agregarHecho(Hecho hecho) {
    entityManager().persist(hecho);

  }

  public void limpiar() { //para testear
    this.hechos.clear();
  }

  @SuppressWarnings("unchecked")
  public boolean contiene(Hecho hecho) {
    Hecho hechoEncontrado = entityManager()
        .find(Hecho.class, hecho.getId());

    return hechoEncontrado != null;

  }

  public Boolean verificaConsenso(Hecho hechoAverificar, TipoDeConsenso consenso) {
    if (consenso == null) {
      return true;
    }

    for (Hecho hechoDelRepositorio : this.hechos) {
      if (hechoDelRepositorio.comparacionRigurosa(hechoAverificar)) {
        return hechoDelRepositorio.getConsensos().contains(consenso);
      }
    }
    return false;
  }
}
