package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.dto.SolicitudAgregacionDto;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.models.enums.OrigenHecho;
import ar.edu.utn.frba.dds.repositories.SolicitudAgregacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class Hecho2Test {
  @BeforeEach
  public void resetRepository() {
    SolicitudAgregacionRepository.resetInstance();
  }

  @Test
  public void testHechoConSugerenciasDevuelveTrue() {
    // Instancia real de Hecho
    Hecho hecho = new Hecho(
        "titulo", "descripcion", "categoria",
        0.00, 0.00, LocalDate.now(), OrigenHecho.ESTATICO, "", 1
    );

    CambiosHechoDto sugerencias = new CambiosHechoDto();

    // Armar DTO y agregar solicitud al repositorio
    SolicitudAgregacionDto dto = new SolicitudAgregacionDto(false, hecho);

    SolicitudAgregacionRepository repo = SolicitudAgregacionRepository.getInstance();
    repo.agregarSolicitud(dto);

    // Obtener el ID generado automáticamente (es 1 en este caso)
    int id = 1; // porque empieza en biggestId = 0 y se incrementa al agregar

    repo.aceptarSolicitudConSugerencias(id, sugerencias);

    assertTrue(hecho.tieneSugerencias());
  }


}
