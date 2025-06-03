package ar.edu.utn.frba.dds;


import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteDinamica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;


public class FuenteDinamicaTest {

  @Test
  public void obtenerHechosDevuelveSoloSolicitudesAceptadas() {

    Hecho hechoMock1 = mock(Hecho.class);
    Hecho hechoMock2 = mock(Hecho.class);
    Hecho hechoMock3 = mock(Hecho.class);

    SolicitudAgregacion s1 = new SolicitudAgregacion(hechoMock1);
    SolicitudAgregacion s2 = new SolicitudAgregacion(hechoMock2);
    SolicitudAgregacion s3 = new SolicitudAgregacion(hechoMock3);

    s1.aceptarSolicitud(); // debe incluirse
    s2.rechazarSolicitud(); // no debe incluirse
    s3.aceptarSolicitudConSugerencias(); // debe incluirse

    FuenteDinamica fuente = new FuenteDinamica(Arrays.asList(s1, s2, s3));
    List<Hecho> hechos = fuente.obtenerHechos();

    assertEquals(2, hechos.size());
    assertTrue(hechos.contains(hechoMock1));
    assertTrue(hechos.contains(hechoMock3));
    assertFalse(hechos.contains(hechoMock2));

  }
}
