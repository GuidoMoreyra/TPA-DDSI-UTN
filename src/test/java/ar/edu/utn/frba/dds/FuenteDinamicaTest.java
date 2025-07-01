package ar.edu.utn.frba.dds;


import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteDinamica;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

public class FuenteDinamicaTest {

  @Test
  public void obtenerHechosDevuelveSoloSolicitudesAceptadas() {

    Hecho hechoMock1 = mock(Hecho.class);
    Hecho hechoMock2 = mock(Hecho.class);
    Hecho hechoMock3 = mock(Hecho.class);

    CambiosHechoDto sugerenciaDtoMock = mock(CambiosHechoDto.class);

    SolicitudAgregacion s1 = new SolicitudAgregacion(hechoMock1, true);
    SolicitudAgregacion s2 = new SolicitudAgregacion(hechoMock2, true);
    SolicitudAgregacion s3 = new SolicitudAgregacion(hechoMock3, true);

    s1.aceptarSolicitud(); // debe incluirse
    s2.rechazarSolicitud(); // no debe incluirse
    s3.aceptarSolicitudConSugerencias(sugerenciaDtoMock); // debe incluirse

    // Simular flujo real de uso: agregar al repositorio directamente
    var repo = SolicitudesAgregacionRepository.getInstance();

    // Hacemos trampa para testear sin depender de agregar por DTO
    repo.agregarSolicitud(s1);
    repo.agregarSolicitud(s2);
    repo.agregarSolicitud(s3);

    s1.aceptarSolicitud();
    s2.rechazarSolicitud();
    s3.aceptarSolicitudConSugerencias(sugerenciaDtoMock);

    FuenteDinamica fuente = new FuenteDinamica();
    List<Hecho> hechos = fuente.obtenerHechos();

    assertEquals(2, hechos.size());
    assertTrue(hechos.contains(hechoMock1));
    assertTrue(hechos.contains(hechoMock3));
    assertFalse(hechos.contains(hechoMock2));
  }
}
