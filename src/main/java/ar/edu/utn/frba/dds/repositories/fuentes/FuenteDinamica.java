package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FuenteDinamica implements Fuente {
  //private List<Hecho> hechosFiltrados = new ArrayList<>();
  private List<SolicitudAgregacion> solicitudes;

  //CONSTRUCTOR
  public FuenteDinamica(List<SolicitudAgregacion> solicitudes) {
    this.solicitudes = solicitudes;
  }

  @Override
  public List<Hecho> obtenerHechos() {
    return solicitudes.stream()
        .filter(s -> s.getEstado() == EstadoSolicitudAgregacion.ACEPTADO
            || s.getEstado() == EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS)
        .map(SolicitudAgregacion::getHecho)
        .collect(Collectors.toList());
  }
}