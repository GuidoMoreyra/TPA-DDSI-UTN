package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class HechoTest {



  @Test
  public void sePuedeCrearUnHechoYConsultarDatos() {
    Hecho hecho = new Hecho(
        "Incendio",
        "Fuego en el bosque",
        "Incendio Forestal",
        -34.6,
        -58.4,
        LocalDate.of(2023, 10, 5),
        OrigenHecho.ESTATICO,
        "foto.png"
    );

    assertEquals("Incendio", hecho.getTitulo());
    assertEquals("Fuego en el bosque", hecho.getDescripcion());
    assertEquals("Incendio Forestal", hecho.getCategoria());
    assertEquals("foto.png", hecho.getContenidoMultimedia());
    assertEquals(LocalDate.of(2023, 10, 5), hecho.getFechaDelHecho());
    assertEquals(OrigenHecho.ESTATICO, hecho.getOrigen());

    Coordenada coord = hecho.getCoordenadas();
    assertEquals(-34.6, coord.getLatitud());
    assertEquals(-58.4, coord.getLongitud());
  }

  @Test
  public void sePuedenAplicarCambiosSobreUnHecho() {
    Hecho hecho = new Hecho(
        "Incendio",
        "Fuego en el bosque",
        "Incendio Forestal",
        -34.6,
        -58.4,
        LocalDate.of(2023, 10, 5),
        OrigenHecho.ESTATICO,
        "foto.png"
    );

    CambiosHechoDto cambios = new CambiosHechoDto();
    cambios.setTitulo("Incendio Actualizado");
    cambios.setDescripcion("Descripción actualizada");
    cambios.setCategoria("Nuevo Tipo");
    cambios.setContenidoMultimedia("nuevo.png");
    cambios.setOrigen(OrigenHecho.DINAMICO);

    hecho.aplicarCambios(cambios);

    assertEquals("Incendio Actualizado", hecho.getTitulo());
    assertEquals("Descripción actualizada", hecho.getDescripcion());
    assertEquals("Nuevo Tipo", hecho.getCategoria());
    assertEquals("nuevo.png", hecho.getContenidoMultimedia());
    assertEquals(OrigenHecho.DINAMICO, hecho.getOrigen());
  }

  @Test
  public void siElHechoEstaActivoDevuelveTrue() {
    Hecho hecho = new Hecho(
        "Titulo",
        "Descripcion",
        "Categoria",
        -34.5,
        -58.4,
        LocalDate.of(2024, 1, 1),
        OrigenHecho.ESTATICO,
        "imagen.jpg"
    );
    String justificacion = "a".repeat(501);
    SolicitudEliminacion solicitudEliminacion = new SolicitudEliminacion(hecho, justificacion);
    solicitudEliminacion.modificarEstado(EstadoSolicitudEliminacion.APROBADO);

    //pruebo el metodo
    assertTrue(hecho.estaActivo(), "Un hecho sin solicitudes aprobadas debería estar activo");

  }

  @Test
  public void testHechoConSugerenciasDevuelveTrue() {
    // Instancia real de Hecho
    Hecho hecho = new Hecho(
        "titulo", "descripcion", "categoria",
        0.00, 0.00, LocalDate.now(), OrigenHecho.ESTATICO, ""
    );

    CambiosHechoDto sugerencias = new CambiosHechoDto();

    // Armar DTO y agregar solicitud al repositorio
    SolicitudAgregacion solicitudAgregacion = new SolicitudAgregacion(hecho, false);

    SolicitudesAgregacionRepository repo = SolicitudesAgregacionRepository.getInstance();
    repo.agregarSolicitud(solicitudAgregacion);

    // Obtener el ID generado automáticamente (es 1 en este caso)
    int id = 1; // porque empieza en id = 0 y se incrementa al agregar

    solicitudAgregacion.aceptarSolicitudConSugerencias(sugerencias);

    assertTrue(hecho.tieneSugerencias());
  }


}
