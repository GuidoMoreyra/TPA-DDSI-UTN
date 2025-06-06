package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FuenteDinamica implements Fuente {
  private List<SolicitudAgregacion> solicitudes;

  //CONSTRUCTOR
  public FuenteDinamica(List<SolicitudAgregacion> solicitudes) {
    this.solicitudes = new ArrayList<>(solicitudes);
  }

  @Override
  public List<Hecho> obtenerHechos() {
    return solicitudes.stream()
        .filter(
            s -> List.of(
                EstadoSolicitudAgregacion.ACEPTADO,
                EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS)

            .contains(s.getEstado()))
        .map(SolicitudAgregacion::getHecho)
        .collect(Collectors.toList());
  }
}