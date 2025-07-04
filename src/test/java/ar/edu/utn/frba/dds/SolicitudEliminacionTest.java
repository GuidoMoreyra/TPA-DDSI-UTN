package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class SolicitudEliminacionTest {
  private Hecho hechoMock;
  private String justificacion;

  @BeforeEach
  public void setUp() {
    hechoMock = mock(Hecho.class);
    justificacion = "a".repeat(501);
  }

  @Test
  public void seCreaConEstadoPendiente() {
    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoMock, justificacion);

    assertEquals(hechoMock, solicitud.getHecho());
    assertEquals(justificacion, solicitud.getJustificacion());
    assertEquals(EstadoSolicitudEliminacion.PENDIENTE, solicitud.getEstado());

  }

  @Test
  void lanzaExcepcionSiLaJustificacionEsMuyCorta() {
    justificacion = "Muy corta";
    assertThrows(IllegalArgumentException.class, () ->
        new SolicitudEliminacion(hechoMock, justificacion)
    );
  }

  @Test
  void permiteModificarElEstadoAAprobado() {

    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoMock, justificacion);
    solicitud.modificarEstado(EstadoSolicitudEliminacion.APROBADO);

    assertEquals(EstadoSolicitudEliminacion.APROBADO, solicitud.getEstado());

  }

  @Test
  void permiteModificarElEstadoARechazado() {

    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoMock, justificacion);
    solicitud.modificarEstado(EstadoSolicitudEliminacion.RECHAZADO);

    assertEquals(EstadoSolicitudEliminacion.RECHAZADO, solicitud.getEstado());

  }
}
