package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.dto.SolicitudEliminacionDto;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudEliminacion;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudRepositorySingleton {
  private SolicitudRepositorySingleton intancia = null;
  private List<SolicitudEliminacion> pendientes;
  private List<SolicitudEliminacion> aprobados;
  private List<SolicitudEliminacion> rechazados;
  private int biggestId;

  private SolicitudRepositorySingleton() {
    pendientes = new ArrayList<>();
    aprobados = new ArrayList<>();
    rechazados = new ArrayList<>();
    biggestId = 0;
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

      default -> throw new RuntimeException("Estado invalido");
    }
  }


  ///Dado el id de la solicitud lo cambia a la lista de rechazados.
  public void rechazarSolicitud(int idSolicitud) {
    try {
      var solicitudRechazada =
          pendientes
              .stream()
              .filter(s -> s.getId() == idSolicitud)
              .findFirst()
              .orElseThrow(() -> new NoSuchObjectException("Id inválido"));

      pendientes.remove(solicitudRechazada);
      rechazados.add(solicitudRechazada);

    } catch (NoSuchObjectException e) {
      throw new RuntimeException("No se encontro la solicitud");
    }
  }

  ///Acepta la solicitud y elimina el hecho
  public void aceptarSolicitud(int idSolicitud) {
    try {
      var solicitudAceptada =
          pendientes
              .stream()
              .filter(s -> s.getId() == idSolicitud)
              .findFirst()
              .orElseThrow(() -> new NoSuchObjectException("Id inválido"));

      pendientes.remove(solicitudAceptada);
      aprobados.add(solicitudAceptada);

      //Aca deberia agregarse una funcion para eliminarlo de todos lados

    } catch (NoSuchObjectException e) {
      throw new RuntimeException("No se encontro la solicitud");
    }
  }
}



