package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudEliminacion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SolicitudRepositorySingleton {


  private static SolicitudRepositorySingleton instancia = null;
  private List<SolicitudEliminacion> pendientesEliminacion;

  private List<SolicitudEliminacion> aprobados;
  private List<SolicitudEliminacion> rechazados;
  private List<SolicitudEliminacion> rechazadosAutomaticamente;

  private List<SolicitudAgregacion> pendientesAgregacion;
  private List<SolicitudAgregacion> aceptadas;
  private List<SolicitudAgregacion> aceptadasConSugerencias;
  private List<SolicitudAgregacion> rechazadas;

  private SolicitudRepositorySingleton() {
    pendientesEliminacion = new ArrayList<>();
    aprobados = new ArrayList<>();
    rechazados = new ArrayList<>();
    rechazadosAutomaticamente = new ArrayList<>();
    pendientesAgregacion = new ArrayList<>();
    aceptadas = new ArrayList<>();
    aceptadasConSugerencias = new ArrayList<>();
    rechazadas = new ArrayList<>();
  }

  public static SolicitudRepositorySingleton getInstance() {
    if (instancia == null) {
      instancia = new SolicitudRepositorySingleton();
    }
    return instancia;
  }


  public static void resetInstance() {
    instancia = new SolicitudRepositorySingleton();
  }

  /// SOLICITUDES ELIMINACION

  public void agregarSolicitudEliminacion(SolicitudEliminacion solicitud) {
    pendientesEliminacion.add(solicitud);
  }

  public List<SolicitudEliminacion> obtenerSolicitudesEliminacionSegunEstado(
      EstadoSolicitudEliminacion estado
  ) {
    switch (estado) {
      case RECHAZADO -> {
        return new ArrayList<>(rechazados);
      }
      case APROBADO -> {
        return new ArrayList<>(aprobados);
      }

      case PENDIENTE -> {
        return new ArrayList<>(pendientesEliminacion);
      }

      case RECHAZADO_AUTOMATICAMENTE -> {
        return new ArrayList<>(rechazadosAutomaticamente);
      }

      default -> throw new RuntimeException("Estado invalido");
    }
  }

  public void rechazarSolicitudEliminacion(SolicitudEliminacion solicitud) {
    if (!pendientesEliminacion.remove(solicitud)) {
      throw new IllegalArgumentException("La solicitud no está pendiente");
    }
    solicitud.modificarEstado(EstadoSolicitudEliminacion.RECHAZADO);
    rechazados.add(solicitud);
  }

  ///Acepta la solicitud y elimina el hecho
  public void aceptarSolicitudEliminacion(SolicitudEliminacion solicitud) {
    if (!pendientesEliminacion.remove(solicitud)) {
      throw new IllegalArgumentException("La solicitud no está pendiente");
    }
    solicitud.modificarEstado(EstadoSolicitudEliminacion.APROBADO);
    aprobados.add(solicitud);

    // TODO: Agregar aquí la lógica que elimina el hecho de la fuente
  }

  /// SOLICITUDES AGREGACION

  public void agregarSolicitudAgregacion(SolicitudAgregacion solicitud) {
    pendientesAgregacion.add(solicitud);
  }

  public List<SolicitudAgregacion> obtenerSolicitudesAgregacionSegunEstado(
      EstadoSolicitudAgregacion estado
  ) {
    switch (estado) {
      case PENDIENTE -> {
        return new ArrayList<>(pendientesAgregacion);
      }
      case RECHAZADO -> {
        return new ArrayList<>(rechazadas);
      }
      case ACEPTADO -> {
        return new ArrayList<>(aceptadas);
      }
      case ACEPTADO_CON_SUGERENCIAS -> {
        return new ArrayList<>(aceptadasConSugerencias);
      }

      default -> throw new RuntimeException("Estado invalido");
    }

  }

  public void aceptarSolicitud(SolicitudAgregacion solicitud) {
    if (!pendientesAgregacion.remove(solicitud)) {
      throw new IllegalArgumentException("La solicitud no está pendiente");
    }
    solicitud.aceptarSolicitud();
    aceptadas.add(solicitud);
  }

  public void aceptarSolicitudConSugerencias(
      SolicitudAgregacion solicitud,
      CambiosHechoDto sugerencias
  ) {
    if (!pendientesAgregacion.remove(solicitud)) {
      throw new IllegalArgumentException("La solicitud no está pendiente");
    }
    solicitud.aceptarSolicitudConSugerencias(sugerencias);
    aceptadasConSugerencias.add(solicitud);
  }

  public void rechazarSolicitudAgregacion(SolicitudAgregacion solicitud) {
    if (!pendientesAgregacion.remove(solicitud)) {
      throw new IllegalArgumentException("La solicitud no está pendiente");
    }
    solicitud.rechazarSolicitud();
    rechazadas.add(solicitud);
  }

}



