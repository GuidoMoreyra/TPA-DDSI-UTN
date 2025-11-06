package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.models.ComponenteDeEstadisticas;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;

public class MainEstadisticas {
  public static void main(String[] args) {

    ColeccionRepository coleccionRepository = new ColeccionRepository();

    SolicitudesEliminacionRepository solicitudesEliminacionRepository =
        SolicitudesEliminacionRepository.getInstance();

    HechosRepository hechosRepository = HechosRepository.getInstance();

    ComponenteDeEstadisticas componente = new ComponenteDeEstadisticas(
          coleccionRepository,
          solicitudesEliminacionRepository,
          hechosRepository,
        "Inseguridad",
        null
    );

    componente.actualizar("Inseguridad", null);

  }
}
