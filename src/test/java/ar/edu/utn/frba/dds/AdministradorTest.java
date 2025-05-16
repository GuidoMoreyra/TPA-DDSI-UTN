package ar.edu.utn.frba.dds;
/*
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.hecho.dto.HechoLugarDto;
import ar.edu.utn.frba.dds.hecho.enums.EstadoDelHecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.models.Hecho;
import ar.edu.utn.frba.dds.usuario.models.Administrador;
import ar.edu.utn.frba.dds.usuario.models.Visualizador;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class AdministradorTest {

  private Hecho hecho;
  private Hecho otroHecho;
  private Administrador admin;
  private HechoLugarDto lugar;
  private String justificacionLarga;
  private Visualizador solicitante;


  @BeforeEach
  public void setUp() {
    // Instancias que se crean antes de cada test
    lugar = new HechoLugarDto(1.0, 1.0);
    hecho
        = new Hecho("Título de prueba",
        "Descripción del hecho de prueba",
        "Categoría ejemplo",
        lugar,
        LocalDate.of(2023, 5, 20),
        OrigenHecho.DINAMICA);

    otroHecho = new Hecho("Título de prueba",
        "Descripción del hecho de prueba",
        "Categoría ejemplo",
        lugar,
        LocalDate.of(2023, 5, 20),
        OrigenHecho.DINAMICA);

    admin = new Administrador(1, "Admin", "Istrador", 40);

    justificacionLarga = "Esto es una justificación suficientemente larga. ".repeat(15);

    solicitante = new Visualizador(1, "visual", "izador", 35); // Constructor de ejemplo

  }

  @Test
  public void testAprobacionDeSolicitud() {
    SolicitudEliminacion solicitud = new SolicitudEliminacion(hecho, solicitante, justificacionLarga);
    admin.aprobar(solicitud);

    assertEquals(EstadoSolicitudEliminacion.APROBADO, solicitud.getEstado());
    assertEquals(EstadoDelHecho.INACTIVO, hecho.getEstado());
  }

  @Test
  public void testRechazoDeSolicitud() {
    SolicitudEliminacion solicitud = new SolicitudEliminacion(otroHecho, solicitante, justificacionLarga);
    admin.rechazar(solicitud);

    assertEquals(EstadoSolicitudEliminacion.RECHAZADO, solicitud.getEstado());
    assertEquals(EstadoDelHecho.ACTIVO, hecho.getEstado());
  }
}*/
