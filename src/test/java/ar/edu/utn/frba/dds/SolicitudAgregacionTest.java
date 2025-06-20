package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

public class SolicitudAgregacionTest {
  private Hecho hechoMock;

  @BeforeEach
  void setUp() {
    hechoMock = mock(Hecho.class);
  }

  @Test
  void alAceptarUnaSolicitudCambiaEstadoAAceptado() {
    SolicitudAgregacion solicitud = new SolicitudAgregacion(hechoMock, false);

    solicitud.aceptarSolicitud();

    assertEquals(EstadoSolicitudAgregacion.ACEPTADO, solicitud.getEstado());
  }

  @Test
  void alRechazarSolicitudCambiaEstadoARechazado() {
    SolicitudAgregacion solicitud = new SolicitudAgregacion(hechoMock, false);

    solicitud.rechazarSolicitud();

    assertEquals(EstadoSolicitudAgregacion.RECHAZADO, solicitud.getEstado());
  }
  @Test
  void alAceptarConSugerenciasSeAplicanCambiosYEstadoEsAceptadoConSugerencias() {
    SolicitudAgregacion solicitud = new SolicitudAgregacion(hechoMock, false);
    CambiosHechoDto cambios = new CambiosHechoDto();
    cambios.setDescripcion("Nueva descripción");

    solicitud.aceptarSolicitudConSugerencias(cambios);

    verify(hechoMock).aplicarCambios(cambios);
    assertEquals(EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS, solicitud.getEstado());
  }

  @Test
  void puedeEditarDevuelveTrueSiNoEsAnonimoYFechaReciente() {
    SolicitudAgregacion solicitud = new SolicitudAgregacion(hechoMock, false);

    assertTrue(solicitud.puedeEditar());
  }

  @Test
  void puedeEditarDevuelveFalseSiEsAnonimo() {
    SolicitudAgregacion solicitud = new SolicitudAgregacion(hechoMock, true);

    assertFalse(solicitud.puedeEditar());
  }

  @Test
  void puedeEditarDevuelveFalseSiPasaronMasDe7Dias() {
    SolicitudAgregacion solicitud = new SolicitudAgregacion(hechoMock, false);
    // Forzamos la fecha a hace más de 7 días usando un constructor especial para testing
    var fechaAntigua = LocalDate.now().minusDays(8);
    // Se agregás un constructor especial solo para el test
    SolicitudAgregacion solicitudConFechaAntigua = new SolicitudAgregacion(hechoMock, false, fechaAntigua);

    assertFalse(solicitudConFechaAntigua.puedeEditar());
  }
}

