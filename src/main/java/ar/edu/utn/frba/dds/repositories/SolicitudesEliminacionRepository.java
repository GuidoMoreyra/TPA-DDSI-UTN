package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.models.DetectorDeSpamBasico;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public final class SolicitudesEliminacionRepository implements WithSimplePersistenceUnit {
  private static final SolicitudesEliminacionRepository INSTANCE =
      new SolicitudesEliminacionRepository();

  private final List<SolicitudEliminacion> solicitudes = new ArrayList<>();

  private SolicitudesEliminacionRepository() {}

  public static SolicitudesEliminacionRepository getInstance() {
    return INSTANCE;
  }

  @SuppressWarnings("unchecked")
  public List<SolicitudEliminacion> getSolicitudes() {
    return entityManager()
        .createQuery("from SolicitudEliminacion", SolicitudEliminacion.class)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<SolicitudEliminacion> obtenerSolicitudesConEstado(EstadoSolicitudEliminacion estado) {
    return entityManager()
        .createQuery("from solicitudeliminacion where estado = :estado")
        .setParameter("estado", estado)
        .getResultList();
  }

  public void agregarSolicitud(SolicitudEliminacion solicitud) {
    entityManager().persist(solicitud);
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
}