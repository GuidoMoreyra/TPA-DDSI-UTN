package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.Coleccion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.List;

public class ColeccionRepository implements WithSimplePersistenceUnit {

  public List<Coleccion> listar() {
    return entityManager()
        .createQuery("from Coleccion", Coleccion.class)
        .getResultList();
  }

  public Coleccion obtener(Long id) {
    return entityManager().find(Coleccion.class, id);
  }

  public void persistir(Coleccion coleccion) {
    entityManager().persist(coleccion);
  }

}
