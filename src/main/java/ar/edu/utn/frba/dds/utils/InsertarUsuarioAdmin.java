package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.models.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import org.apache.commons.codec.digest.DigestUtils;

public class InsertarUsuarioAdmin implements WithSimplePersistenceUnit {

  public static void main(String[] args) {
    new InsertarUsuarioAdmin().insertar();
  }

  public void insertar() {
    withTransaction(
        () -> {
          // Crear usuario admin
          Usuario admin = new Usuario();

          // Usar reflection para setear nivelDeAcceso ya que no hay setter público
          try {
            java.lang.reflect.Field nombreField = Usuario.class.getDeclaredField("nombre");
            nombreField.setAccessible(true);
            nombreField.set(admin, "admin");

            java.lang.reflect.Field passwordField = Usuario.class.getDeclaredField("hashPassorwd");
            passwordField.setAccessible(true);
            passwordField.set(admin, DigestUtils.sha256Hex("admin123"));

            java.lang.reflect.Field nivelField = Usuario.class.getDeclaredField("nivelDeAcceso");
            nivelField.setAccessible(true);
            nivelField.set(admin, 1);

            entityManager().persist(admin);

            System.out.println("✓ Usuario admin creado exitosamente");
            System.out.println("  - Usuario: admin");
            System.out.println("  - Password: admin123");
            System.out.println("  - Nivel de acceso: 1 (Administrador)");
          } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al crear usuario admin", e);
          }
        });
  }
}
