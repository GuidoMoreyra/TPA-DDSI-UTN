package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();
        System.out.println("Servidor iniciado en http://localhost:9001");
        System.out.println("Accede a http://localhost:9001/login para iniciar sesión");
    }
}
