package ar.edu.utn.frba.dds.utils;

import javax.persistence.Persistence;

public class EntityManagerFactory {

  public static void main(String[] args) {
    // El nombre debe coincidir con el persistence-unit del persistence.xml
    String persistenceUnitName = "simple-persistence-unit";

    System.out.println("Inicializando EntityManagerFactory...");
    javax.persistence.EntityManagerFactory emf = Persistence.createEntityManagerFactory(persistenceUnitName);

    System.out.println("EntityManagerFactory inicializado. Hibernate debería haber aplicado las migraciones.");

    emf.close();
    System.out.println("Factory cerrado. Listo.");
  }

}
