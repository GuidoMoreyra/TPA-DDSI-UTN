package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.dto.SolicitudEliminacionDto;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudEliminacion;
import java.util.ArrayList;
import java.util.List;

public class SolicitudRepositorySingleton {
  private static SolicitudRepositorySingleton instancia = null;
  private final List<SolicitudEliminacion> pendientes;
  private final List<SolicitudEliminacion> aprobados;
  private final List<SolicitudEliminacion> rechazados;
  private final List<SolicitudEliminacion> rechazadosAutomaticamente;
  private int biggestId;

  private SolicitudRepositorySingleton() {
    pendientes = new ArrayList<>();
    aprobados = new ArrayList<>();
    rechazados = new ArrayList<>();
    rechazadosAutomaticamente = new ArrayList<>();
    biggestId = 0;
  }

  public static SolicitudRepositorySingleton getInstance() {
    if (instancia == null) {
      instancia = new SolicitudRepositorySingleton();
    }
    return instancia;
  }

  ///Agrega una nueva solicitud  dado el dto.
  ///Se asume que el dto es valido.
  public void agregarSolicitud(SolicitudEliminacionDto dto) {
    var nuevaSolicitud = new SolicitudEliminacion(
        ++biggestId,
        dto.hecho,
        dto.justificacion
    );
    pendientes.add(nuevaSolicitud);
  }

  public List<SolicitudEliminacion> obtenerSolicitudesSegunEstado(
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
        return new ArrayList<>(pendientes);
      }

      case RECHAZADO_AUTOMATICAMENTE -> {
        return new ArrayList<>(rechazadosAutomaticamente);
      }

      default -> throw new RuntimeException("Estado invalido");
    }
  }

  public void rechazarSolicitud(int idSolicitud) {
    var solicitudRechazada =
        pendientes
            .stream()
            .filter(s -> s.getId() == idSolicitud)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No se encontró la solicitud"));

    pendientes.remove(solicitudRechazada);
    rechazados.add(solicitudRechazada);
  }

  ///Acepta la solicitud y elimina el hecho
  public void aceptarSolicitud(int idSolicitud) {
    var solicitudAceptada =
        pendientes
            .stream()
            .filter(s -> s.getId() == idSolicitud)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No se encontró la solicitud"));

    pendientes.remove(solicitudAceptada);
    aprobados.add(solicitudAceptada);
  }
}



