package ar.edu.utn.frba.dds.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityManagerFactory {

  public static void main(String[] args) {
    String persistenceUnitName = "simple-persistence-unit";

    // 1. Capturamos las variables de entorno de Railway
    Map<String, String> env = System.getenv();
    Map<String, Object> configOverrides = new HashMap<>();

    // 2. Si existen las variables de Railway, las usamos.
    // Si no (en tu PC), usamos los valores por defecto que ya conocés.
    String jdbcUrl = env.getOrDefault("MYSQL_URL", "jdbc:mysql://localhost:3306/tp-ddsi");
    String jdbcUser = env.getOrDefault("MYSQLUSER", "root");
    String jdbcPassword = env.getOrDefault("MYSQLPASSWORD", "hola");

    configOverrides.put("javax.persistence.jdbc.url", jdbcUrl);
    configOverrides.put("javax.persistence.jdbc.user", jdbcUser);
    configOverrides.put("javax.persistence.jdbc.password", jdbcPassword);

    // Opcional: Forzar el driver moderno si el XML tiene el viejo
    configOverrides.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");

    System.out.println("Inicializando EntityManagerFactory conectando a: " + jdbcUrl);

    // 3. Pasamos el mapa como segundo argumento
    javax.persistence.EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(persistenceUnitName, configOverrides);

    crearIndicesFullText(emf);

    System.out.println("EntityManagerFactory inicializado correctamente.");

    // OJO: Si este es un servidor Web, NO deberías cerrar el emf acá.
    // Pero si es solo una tarea de migración, está bien.
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
