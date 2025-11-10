package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.Usuario;
import ar.edu.utn.frba.dds.repositories.UsuarioRepository;
import ar.edu.utn.frba.dds.seeders.DatabaseSeeder;
import ar.edu.utn.frba.dds.server.Server;

public class Main {
    public static void main(String[] args) {
        // Crear usuario admin por defecto si no existe
        crearAdminPorDefecto();

        // Poblar la base de datos con datos de ejemplo
        DatabaseSeeder seeder = new DatabaseSeeder();
        seeder.seed();

        Server server = new Server();
        server.start();
        System.out.println("Servidor iniciado en http://localhost:9001");
        System.out.println("Accede a http://localhost:9001/login para iniciar sesión");
        System.out.println("\n===== CREDENCIALES ADMIN POR DEFECTO =====");
        System.out.println("Usuario: admin");
        System.out.println("Password: admin123");
        System.out.println("===========================================\n");
    }

    private static void crearAdminPorDefecto() {
        Usuario admin = UsuarioRepository.INSTANCE.getUsuarioPorNombre("admin");

        if (admin == null) {
            Usuario nuevoAdmin = new Usuario("admin", "admin123");
            nuevoAdmin.setNivelDeAcceso(1);
            UsuarioRepository.INSTANCE.persistUsuario(nuevoAdmin);
            System.out.println("Usuario admin creado con éxito");
        }
    }
}
