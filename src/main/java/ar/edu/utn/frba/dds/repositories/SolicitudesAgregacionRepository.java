package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public final class SolicitudesAgregacionRepository {
  private static final SolicitudesAgregacionRepository INSTANCE =
      new SolicitudesAgregacionRepository();

  private final List<SolicitudAgregacion> solicitudes = new ArrayList<>();

  private SolicitudesAgregacionRepository() {}

  public static SolicitudesAgregacionRepository getInstance() {
    return INSTANCE;
  }

  public List<SolicitudAgregacion> getSolicitudes() {
    return Collections.unmodifiableList(solicitudes);
  }




  public List<SolicitudAgregacion> obtenerSolicitudesConEstado(EstadoSolicitudAgregacion estado) {
    return solicitudes
        .stream()
        .filter(s -> Objects.equals(s.getEstado(), estado))
        .toList();
  }

  public void agregarSolicitud(SolicitudAgregacion solicitud) {

    solicitudes.add(solicitud);
  }
}