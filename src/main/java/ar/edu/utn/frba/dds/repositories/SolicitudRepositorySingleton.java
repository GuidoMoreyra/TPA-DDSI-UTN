package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.dto.SolicitudAgregacionDto;
import ar.edu.utn.frba.dds.dto.SolicitudEliminacionDto;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudEliminacion;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
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

  private int id;

  private SolicitudRepositorySingleton() {
    pendientesEliminacion = new ArrayList<>();
    aprobados = new ArrayList<>();
    rechazados = new ArrayList<>();
    rechazadosAutomaticamente = new ArrayList<>();
    pendientesAgregacion = new ArrayList<>();
    aceptadas = new ArrayList<>();
    aceptadasConSugerencias = new ArrayList<>();
    rechazadas = new ArrayList<>();
    id = 0;
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

  public void agregarSolicitudEliminacion(SolicitudEliminacionDto dto) {
    var nuevaSolicitud = new SolicitudEliminacion(
        ++id,
        dto.hecho,
        dto.justificacion
    );
    pendientesEliminacion.add(nuevaSolicitud);
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


  ///Dado el id de la solicitud lo cambia a la lista de rechazados.
  public void rechazarSolicitudEliminacion(int idSolicitud) {
    var solicitudRechazada =
        pendientesEliminacion
            .stream()
            .filter(s -> s.getId() == idSolicitud)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Id inválido"));

    solicitudRechazada.modificarEstado(EstadoSolicitudEliminacion.RECHAZADO);
    pendientesEliminacion.remove(solicitudRechazada);
    rechazados.add(solicitudRechazada);

  }

  ///Acepta la solicitud y elimina el hecho
  public void aceptarSolicitudEliminacion(int idSolicitud) {
    var solicitudAceptada =
        pendientesEliminacion
            .stream()
            .filter(s -> s.getId() == idSolicitud)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Id inválido"));

    solicitudAceptada.modificarEstado(EstadoSolicitudEliminacion.APROBADO);
    pendientesEliminacion.remove(solicitudAceptada);
    aprobados.add(solicitudAceptada);

  }

  /// SOLICITUDES AGREGACION

  public void agregarSolicitudAgregacion(SolicitudAgregacionDto dto) {
    var nuevaSolicitud = new SolicitudAgregacion(
        ++id,
        dto.hecho,
        dto.esAnonimo
    );
    pendientesAgregacion.add(nuevaSolicitud);
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

  public void aceptarSolicitud(Integer id) {
    var solicitudAceptada =
        pendientesAgregacion
            .stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Id inválido"));

    solicitudAceptada.aceptarSolicitud();
    pendientesAgregacion.remove(solicitudAceptada);
    aceptadas.add(solicitudAceptada);
  }

  public void aceptarSolicitudConSugerencias(Integer id, CambiosHechoDto sugerencias) {
    var solicitudAprobadaConSugerencias =
        pendientesAgregacion
            .stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Id inválido"));

    solicitudAprobadaConSugerencias.aceptarSolicitudConSugerencias(sugerencias);
    pendientesAgregacion.remove(solicitudAprobadaConSugerencias);
    aceptadasConSugerencias.add(solicitudAprobadaConSugerencias);

  }

  public void rechazarSolicitudAgregacion(Integer id) {

    var solicitudRechazada =
        pendientesAgregacion
            .stream()
            .filter(s -> s.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Id inválido"));

    solicitudRechazada.rechazarSolicitud();
    pendientesAgregacion.remove(solicitudRechazada);
    rechazadas.add(solicitudRechazada);
  }



}



