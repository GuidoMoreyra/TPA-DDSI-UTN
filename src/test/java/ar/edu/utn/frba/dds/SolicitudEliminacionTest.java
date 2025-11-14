package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.DetectorDeSpamBasico;
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
  private DetectorDeSpamBasico detectorMock;

  @BeforeEach
  public void setUp() {
    hechoMock = mock(Hecho.class);
    justificacion = "Este hecho contiene información completamente incorrecta y desactualizada que debe ser removida urgentemente de la base de datos. Los datos presentados no corresponden con la realidad y podrían confundir a los usuarios que confían en esta plataforma para obtener información precisa y verificada. Es fundamental mantener la integridad de nuestra base de datos eliminando este tipo de contenido que no cumple con los estándares de calidad requeridos. Los administradores deben revisar cuidadosamente esta solicitud y proceder con la eliminación del hecho lo antes posible para evitar la propagación de información falsa o incorrecta.";
    detectorMock = new DetectorDeSpamBasico();
  }

  @Test
  public void seCreaConEstadoPendiente() {
    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoMock, justificacion, detectorMock);

    assertEquals(hechoMock, solicitud.getHecho());
    assertEquals(justificacion, solicitud.getJustificacion());
    assertEquals(EstadoSolicitudEliminacion.PENDIENTE, solicitud.getEstado());
  }

  @Test
  void lanzaExcepcionSiLaJustificacionEsMuyCorta() {
    justificacion = "Muy corta";
    assertThrows(IllegalArgumentException.class, () ->
        new SolicitudEliminacion(hechoMock, justificacion,detectorMock)
    );
  }

  @Test
  void permiteModificarElEstadoAAprobado() {

    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoMock, justificacion,detectorMock);
    solicitud.modificarEstado(EstadoSolicitudEliminacion.APROBADO);

    assertEquals(EstadoSolicitudEliminacion.APROBADO, solicitud.getEstado());

  }

  @Test
  void permiteModificarElEstadoARechazado() {

    SolicitudEliminacion solicitud = new SolicitudEliminacion(hechoMock, justificacion,detectorMock);
    solicitud.modificarEstado(EstadoSolicitudEliminacion.RECHAZADO);

    assertEquals(EstadoSolicitudEliminacion.RECHAZADO, solicitud.getEstado());

  }
}
