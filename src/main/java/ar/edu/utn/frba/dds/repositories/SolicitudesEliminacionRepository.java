package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudEliminacion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class SolicitudesEliminacionRepository {
  private static final SolicitudesEliminacionRepository INSTANCE =
      new SolicitudesEliminacionRepository();

  private final List<SolicitudEliminacion> solicitudes = new ArrayList<>();

  private SolicitudesEliminacionRepository() {}

  public static SolicitudesEliminacionRepository getInstance() {
    return INSTANCE;
  }

  public List<SolicitudEliminacion> getSolicitudes() {
    return Collections.unmodifiableList(solicitudes);
  }

  public List<SolicitudEliminacion> obtenerSolicitudesConEstado(EstadoSolicitudEliminacion estado) {
    return solicitudes
        .stream()
        .filter(s -> Objects.equals(s.getEstado(), estado))
        .toList();
  }

  public void agregarSolicitud(SolicitudEliminacion solicitud) {
    this.solicitudes.add(solicitud);
  }
}