package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.Hecho;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Query;

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

  @SuppressWarnings("unchecked")
  public void limpiar() { //para testear
    this.hechos.clear();
  }

  @SuppressWarnings("unchecked")
  public void limpiarBase() {
    entityManager().clear();
  }


  @SuppressWarnings("unchecked")
  public boolean contiene(Hecho hecho) {
    Hecho hechoEncontrado = entityManager()
        .find(Hecho.class, hecho.getId());

    return hechoEncontrado != null;
  }

  public Boolean verificaConsenso(Hecho hechoAverificar, AlgoritmoDeConsenso consenso) {
    if (consenso == null) {
      return true;
    }

    Hecho hechoEncontrado = entityManager().find(Hecho.class, hechoAverificar.getId());
    if (hechoEncontrado == null) {
      return false;
    }

    return hechoEncontrado.getConsensos().contains(consenso);
  }

  @SuppressWarnings("unchecked")
  public List<Hecho> buscarPorTexto(String searchTerm) {
    if (searchTerm == null || searchTerm.trim().isEmpty()) {
      return Collections.emptyList();
    }

    // Detectar si estamos usando H2 (para tests) o MySQL (para producción)
    String databaseUrl = entityManager().getEntityManagerFactory()
        .getProperties().get("hibernate.connection.url").toString();

    if (databaseUrl.contains("h2")) {
      // Implementación para H2 (tests) usando LIKE
      String jpql = """
          SELECT h FROM Hecho h
          WHERE LOWER(h.titulo) LIKE LOWER(:searchTerm)
          OR LOWER(h.descripcion) LIKE LOWER(:searchTerm)
          """;

      Query query = entityManager().createQuery(jpql, Hecho.class);
      query.setParameter("searchTerm", "%" + searchTerm.trim().toLowerCase() + "%");
      return query.getResultList();
    } else {
      // Implementación para MySQL (producción) usando MATCH AGAINST
      String sql = """
          SELECT h.*, MATCH(h.titulo, h.descripcion)
          AGAINST(:searchTerm IN NATURAL LANGUAGE MODE) as relevance
          FROM hechos h
          WHERE MATCH(h.titulo, h.descripcion) AGAINST(:searchTerm IN NATURAL LANGUAGE MODE)
          ORDER BY relevance DESC
          """;

      Query query = entityManager().createNativeQuery(sql, Hecho.class);
      query.setParameter("searchTerm", searchTerm.trim());
      return query.getResultList();
    }
  }

  public Provincia buscarProvinciaConMasHechosPorCategoria(String categoria) {

    String query = """
        SELECT h.provincia
                FROM Hecho h
                WHERE h.categoria = :categoria
                GROUP BY h.provincia
                ORDER BY COUNT(h) DESC
        """;

    List<Provincia> resultados = entityManager()
        .createQuery(query, Provincia.class)
        .setParameter("categoria", categoria)
        .setMaxResults(1)
        .getResultList();

    return  resultados.isEmpty() ? Provincia.PROVINCIA_DESCONOCIDA : resultados.get(0);

  }

  public Integer buscarHoraPicoDeHechosSegun(String categoria) {

    String query = """
       SELECT HOUR(h.horaHecho), COUNT(h)
           FROM Hecho h
           WHERE h.categoria = :categoria
           GROUP BY HOUR(h.horaHecho)
           ORDER BY COUNT(h) DESC
           """;
    return (Integer) entityManager()
        .createQuery(query, Object[].class)
        .setParameter("categoria", categoria)
        .setMaxResults(1)
        .getResultList()
        .get(0)[0];
  }

  public String buscarCategoriaConMasHechos() {
    String query = """
        Select h.categoria, count(h.categoria)
        from Hecho h
        group by h.categoria
        order by count(h.categoria) desc
        """;

    Object[] result = entityManager()
        .createQuery(query, Object[].class)
        .getResultList()
        .get(0);

    return (String) result[0];
  }
}
