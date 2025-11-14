package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.Coleccion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.Collection;
import java.util.List;

public class ColeccionRepository implements WithSimplePersistenceUnit {

  public List<Coleccion> listar() {
    return entityManager().createQuery("from Coleccion", Coleccion.class).getResultList();
  }

  public Collection<Coleccion> deCategoria(String categoria) {
    var query = "from Coleccion where categoria = :categoria";
    return entityManager()
        .createQuery(query, Coleccion.class)
        .setParameter("categoria", categoria)
        .getResultList();
  }

  public Coleccion obtener(Long id) {
    return entityManager().find(Coleccion.class, id);
  }

  public void persistir(Coleccion coleccion) {
    entityManager().persist(coleccion);
  }

  public Provincia provinciaConMasHechos(Long coleccionId) {
    String query =
        """
        SELECT h.provincia
        FROM Coleccion c
        JOIN c.hechos h
        WHERE c.id = :coleccionId
        GROUP BY h.provincia
        ORDER BY COUNT(h) DESC
        """;

    List<Provincia> resultados =
        entityManager()
            .createQuery(query, Provincia.class)
            .setParameter("coleccionId", coleccionId)
            .getResultList();

    return resultados.isEmpty() ? Provincia.PROVINCIA_DESCONOCIDA : resultados.get(0);
  }
}
