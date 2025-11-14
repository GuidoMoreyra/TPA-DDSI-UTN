package ar.edu.utn.frba.dds.utils;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityManagerFactory {

  public static void main(String[] args) {
    // El nombre debe coincidir con el persistence-unit del persistence.xml
    String persistenceUnitName = "simple-persistence-unit";

    System.out.println("Inicializando EntityManagerFactory...");
    javax.persistence.EntityManagerFactory emf =
        Persistence.createEntityManagerFactory(persistenceUnitName);

    crearIndicesFullText(emf);

    System.out.println(
        "EntityManagerFactory inicializado. "
            + "Hibernate debería haber aplicado las migraciones.");
    System.out.println(
        "EntityManagerFactory inicializado. "
            + "Hibernate debería haber aplicado las migraciones.");

    emf.close();
    System.out.println("Factory cerrado. Listo.");
  }

  private static void crearIndicesFullText(javax.persistence.EntityManagerFactory emf) {
    EntityManager em = emf.createEntityManager();

    List<String> columnasParaFullTextSearch = List.of("titulo", "descripcion");

    try {
      em.getTransaction().begin();

      System.out.println("Creando índices FULLTEXT...");

      if (!existeIndice(em, "hechos", "idx_multiple_fulltext")) {
        ejecutarSql(
            em,
            "ALTER TABLE hechos ADD FULLTEXT idx_multiple_fulltext "
                + "("
                + String.join(", ", columnasParaFullTextSearch)
                + ")");
        System.out.println("✓ Índice FULLTEXT creado para múltiples columnas");
      } else {
        System.out.println("• Índice múltiple ya existe");
      }

      em.getTransaction().commit();
      System.out.println("Todos los índices FULLTEXT han sido creados exitosamente.");

    } catch (Exception e) {
      em.getTransaction().rollback();
      System.err.println("Error creando índices FULLTEXT: " + e.getMessage());
    } finally {
      em.close();
    }
  }

  private static boolean existeIndice(EntityManager em, String tabla, String nombreIndice) {
    try {
      Query query =
          em.createNativeQuery(
              "SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = DATABASE()"
                  + " AND table_name = :tabla AND index_name = :indice");
      query.setParameter("tabla", tabla);
      query.setParameter("indice", nombreIndice);

      Number count = (Number) query.getSingleResult();
      return count.intValue() > 0;
    } catch (Exception e) {
      System.err.println("Error verificando índice: " + e.getMessage());
      return false;
    }
  }

  private static void ejecutarSql(EntityManager em, String sql) {
    try {
      Query query = em.createNativeQuery(sql);
      query.executeUpdate();
    } catch (Exception e) {
      System.err.println("Error ejecutando SQL: " + sql);
      throw e;
    }
  }
}
