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

public class SolicitudAgregacionRepository {
  private static SolicitudAgregacionRepository instancia = null;
  private List<SolicitudAgregacion> pendientes;
  private List<SolicitudAgregacion> aceptadas;
  private List<SolicitudAgregacion> aceptadasConSugerencias;
  private List<SolicitudAgregacion> rechazadas;
  private int biggestId;

  private SolicitudAgregacionRepository() {
    this.pendientes = new ArrayList<>();
    this.aceptadas = new ArrayList<>();
    this.aceptadasConSugerencias = new ArrayList<>();
    this.rechazadas = new ArrayList<>();
    this.biggestId = 0;
  }

  public static SolicitudAgregacionRepository getInstance() {
    if (instancia == null) {
      instancia = new SolicitudAgregacionRepository();
    }
    return instancia;
  }

  public static void resetInstance() {
    instancia = new SolicitudAgregacionRepository();
  }

  public void agregarSolicitud(SolicitudAgregacionDto dto) {
    var nuevaSolicitud = new SolicitudAgregacion(
        ++biggestId,
        dto.hecho,
        dto.esAnonimo
    );
    pendientes.add(nuevaSolicitud);
  }

  public List<SolicitudAgregacion> obtenerSolicitudesSegunEstado(
      EstadoSolicitudAgregacion estado
  ) {
    switch (estado) {
      case PENDIENTE -> {
        return new ArrayList<>(pendientes);
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

  //Aceptar la solicitud

  public void aceptarSolicitud(Integer id) {
    try {
      var solicitudAceptada =
          pendientes
              .stream()
              .filter(s -> s.getId().equals(id))
              .findFirst()
              .orElseThrow(() -> new NoSuchObjectException("Id inválido"));

      solicitudAceptada.aceptarSolicitud();
      pendientes.remove(solicitudAceptada);
      aceptadas.add(solicitudAceptada);
    } catch (NoSuchObjectException e) {
      throw new RuntimeException("No se encontró la solicitud");
    }
  }

  //Aceptar con sugerencias

  public void aceptarSolicitudConSugerencias(Integer id, CambiosHechoDto sugerencias) {
    try {
      var solicitudAprobadaConSugerencias =
          pendientes
              .stream()
              .filter(s -> s.getId().equals(id))
              .findFirst()
              .orElseThrow(() -> new NoSuchObjectException("Id inválido"));

      solicitudAprobadaConSugerencias.aceptarSolicitudConSugerencias(sugerencias);
      pendientes.remove(solicitudAprobadaConSugerencias);
      aceptadasConSugerencias.add(solicitudAprobadaConSugerencias);

    } catch (NoSuchObjectException e) {
      throw new RuntimeException("No se encontró la solicitud");
    }

  }

  //Rechazar la solicitud

  public void rechazarSolicitud(Integer id) {
    try {
      var solicitudRechazada =
          pendientes
              .stream()
              .filter(s -> s.getId().equals(id))
              .findFirst()
              .orElseThrow(() -> new NoSuchObjectException("Id inválido"));

      solicitudRechazada.rechazarSolicitud();
      pendientes.remove(solicitudRechazada);
      rechazadas.add(solicitudRechazada);

    } catch (NoSuchObjectException e) {
      throw new RuntimeException("No se encontró la solicitud");
    }

  }



}
