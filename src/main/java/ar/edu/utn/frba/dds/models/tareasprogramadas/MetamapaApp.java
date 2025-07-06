package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.contracts.Conexion;
import ar.edu.utn.frba.dds.contracts.TareaProgramada;
import ar.edu.utn.frba.dds.models.ConexionFalsa;
import ar.edu.utn.frba.dds.models.Planificador;
import ar.edu.utn.frba.dds.repositories.fuentes.AdaptadorFuenteDemo;
import java.time.LocalDateTime;


public final class MetamapaApp {
  public static void main(String[] args) {
    Conexion conexion = new ConexionFalsa(); // implementación fake
    AdaptadorFuenteDemo fuente = new AdaptadorFuenteDemo(
        conexion,
        "http://fakeurl.com/api",
        LocalDateTime.now().minusMinutes(1)
    );
    //Creo la tarea programada
    TareaProgramada actualizarFuenteDemo =
        new TareaActualizarFuenteDemo(fuente);

    //Creo el planificador
    Planificador planificador = new Planificador();

    //le agrego las tareas al planificador
    planificador.agregarTarea(actualizarFuenteDemo);

    //Ejecuta todas las tareas programadas
    planificador.ejecutarTareas();

  }
}

