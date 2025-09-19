package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.ComponenteDeEstadisticas;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteEstatica;
import java.time.LocalDate;

public class MainEstadisticas {
  public static void main(String[] args) {

    ColeccionRepository coleccionRepository = new ColeccionRepository();

    SolicitudesEliminacionRepository solicitudesEliminacionRepository =
        SolicitudesEliminacionRepository.getInstance();

    ComponenteDeEstadisticas componente = new ComponenteDeEstadisticas(
          coleccionRepository,
          solicitudesEliminacionRepository,
        "Inseguridad"
    );

    componente.actualizar();

  }
}
