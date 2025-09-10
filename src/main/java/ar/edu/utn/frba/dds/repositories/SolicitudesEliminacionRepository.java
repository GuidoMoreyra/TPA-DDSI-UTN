package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.models.DetectorDeSpamBasico;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
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

  public void rechazarAutomaticamente(List<SolicitudEliminacion> solicitudesDeEliminacion) {
    DetectorDeSpamBasico detectorDeSpam = new DetectorDeSpamBasico();

    solicitudesDeEliminacion.stream()
        .filter(s -> s.getEstado() == EstadoSolicitudEliminacion.PENDIENTE)
        .filter(s -> detectorDeSpam.esSpam(s.getJustificacion()))
        .forEach(s -> s.modificarEstado(EstadoSolicitudEliminacion.RECHAZADO_AUTOMATICAMENTE));
  }

  public void rechazarAutomaticamente() {
    rechazarAutomaticamente(solicitudes);
  }

  /*
  *metodos para estadistica
  *¿Cuántas solicitudes de eliminación son spam?
  * */
  public Long cantidadDeSolicitudesSpam(List<SolicitudEliminacion> solicitudesDeEliminacion) {
    DetectorDeSpamBasico detectorDeSpam = new DetectorDeSpamBasico();

    return solicitudesDeEliminacion.stream()
        .filter(s -> detectorDeSpam.esSpam(s.getJustificacion())).count();

  }
}