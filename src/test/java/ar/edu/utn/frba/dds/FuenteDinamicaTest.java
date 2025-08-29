package ar.edu.utn.frba.dds;


import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteDinamica;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

public class FuenteDinamicaTest {

  @Test
  public void obtenerHechosDevuelveSoloSolicitudesAceptadas() {

    // Crear hechos reales
    Hecho hecho1 = new Hecho(
        "Incendio forestal bariloche",
        "impacto de truenos en arboles genero un incendio",
        "incendio forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 20),
        OrigenHecho.ESTATICO,
        null
    );

    Hecho hecho2 = new Hecho(
        "desborde del rio parana",
        "intensas lluvias genere crecida del rio historica",
        "desborde de rios",
        -38,
        -56,
        LocalDate.of(2022, 10, 21),
        OrigenHecho.ESTATICO,
        null
    );

    Hecho hecho3 = new Hecho(
        "Tormenta de arena en mendoza",
        "inesperada torment de arena cubre todo mendoza",
        "tormenta de arena",
        -38,
        -56,
        LocalDate.of(2022, 10, 22),
        OrigenHecho.ESTATICO,
        null
    );

    CambiosHechoDto sugerenciaDtoMock = mock(CambiosHechoDto.class);

    SolicitudAgregacion s1 = new SolicitudAgregacion(hecho1, true);
    SolicitudAgregacion s2 = new SolicitudAgregacion(hecho2, true);
    SolicitudAgregacion s3 = new SolicitudAgregacion(hecho3, true);

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

    assertTrue(hechos.stream().anyMatch(h->h.getTitulo().equals("Tormenta de arena en mendoza")));
    assertTrue(hechos.stream().anyMatch(h->h.getTitulo().equals("Incendio forestal bariloche")));
    assertFalse(hechos.stream().anyMatch(h->h.getTitulo().equals("desborde del rio parana")));
  }
}
