package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.contracts.Conexion;
import ar.edu.utn.frba.dds.models.ConexionFalsa;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.repositories.fuentes.AdaptadorFuenteDemo;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteIntermedia;
import java.time.LocalDateTime;
import java.util.List;

public class MainDefuenteDemo {

  public static void main(String[] args) {
    Conexion conexion = new ConexionFalsa(); // implementación fake
    AdaptadorFuenteDemo fuente =
        new AdaptadorFuenteDemo(
            conexion, "http://fakeurl.com/api", LocalDateTime.now().minusMinutes(1)
            // deberia ser cada hora en el crontab
            );

    FuenteIntermedia fuentedemo = new FuenteIntermedia();
    fuentedemo.configurarFuenteIntermedia(fuente);

    fuentedemo.actualizar();

    List<Hecho> hechos = fuentedemo.obtenerHechos();
    System.out.println("Hechos obtenidos (" + LocalDateTime.now() + "):");
    for (Hecho hecho : hechos) {
      System.out.println("- " + hecho.getTitulo());
    }
  }
}
