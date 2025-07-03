package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.TareaProgramada;
import java.util.ArrayList;
import java.util.List;

public class Planificador {

  private List<TareaProgramada> tareas = new ArrayList<>();

  public void agregarTarea(TareaProgramada tarea) {
    tareas.add(tarea);
  }

  public void ejecutarTareas() {
    for (TareaProgramada tarea : tareas) {
      tarea.ejecutar();
    }
  }
}
