package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FuenteDinamica implements Fuente {

  @Override
  public List<Hecho> obtenerHechos() {
    var repo = SolicitudesAgregacionRepository.getInstance();

    List<SolicitudAgregacion> aceptadas =
        repo.obtenerSolicitudesConEstado(
            EstadoSolicitudAgregacion.ACEPTADO
        );

    List<SolicitudAgregacion> aceptadasConSugerencias =
        repo.obtenerSolicitudesConEstado(
            EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS
        );

    return Stream
        .concat(aceptadas.stream(), aceptadasConSugerencias.stream())
        .map(SolicitudAgregacion::getHecho)
        .collect(Collectors.toList());
  }


}