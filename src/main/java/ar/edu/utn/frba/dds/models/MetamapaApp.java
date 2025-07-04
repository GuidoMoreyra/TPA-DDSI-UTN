package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Conexion;
import ar.edu.utn.frba.dds.repositories.fuentes.AdaptadorFuenteDemo;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public final class MetamapaApp {
  public static void main(String[] args) {
    Conexion conexion = new ConexionFalsa(); // implementación fake
    AdaptadorFuenteDemo fuente = new AdaptadorFuenteDemo(
        conexion,
        "http://fakeurl.com/api",
        LocalDateTime.now().minusMinutes(1)
    );
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        List<Hecho> hechos = fuente.obtenerHechos();
        System.out.println("Hechos obtenidos (" + LocalDateTime.now() + "):");
        for (Hecho hecho : hechos) {
          System.out.println("- " + hecho.getTitulo());
        }
      }

    };
    Timer timer = new Timer("Timer");
    timer.schedule(task, 0L, 10000L); //0 delay y se repite cada 10seg=10000L
  }
}

