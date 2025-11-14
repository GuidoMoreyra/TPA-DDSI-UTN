package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public final class SolicitudesAgregacionRepository implements WithSimplePersistenceUnit {
  private static final SolicitudesAgregacionRepository INSTANCE =
      new SolicitudesAgregacionRepository();

  private final List<SolicitudAgregacion> solicitudes = new ArrayList<>();

  private SolicitudesAgregacionRepository() {}

  public static SolicitudesAgregacionRepository getInstance() {
    return INSTANCE;
  }

  @SuppressWarnings("unchecked")
  public List<SolicitudAgregacion> getSolicitudes() {
    return entityManager()
        .createQuery("from SolicitudAgregacion", SolicitudAgregacion.class)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public List<SolicitudAgregacion> obtenerSolicitudesConEstado(EstadoSolicitudAgregacion estado) {
    return entityManager()
        .createQuery("from SolicitudAgregacion where estado = :estado")
        .setParameter("estado", estado)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public void agregarSolicitud(SolicitudAgregacion solicitud) {
    entityManager().persist(solicitud);
  }
}
