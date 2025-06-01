package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ar.edu.utn.frba.dds.hecho.dto.HechoLugarDto;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.usuario.contracts.GestorHechos;
import ar.edu.utn.frba.dds.usuario.models.Visualizador;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class VisualizadorTest {

  @Test

  public void unContribuyentePuedeSolicitarEliminarUnHecho() {

    // Arrange
    GestorHechos visualizador = new Visualizador(1, "Juan", "Pérez", 35);
    HechoLugarDto lugar = new HechoLugarDto(1.0, 1.0);
    Hecho hecho = new Hecho("Título de prueba",
        "Descripción del hecho de prueba",
        "Categoría ejemplo",
        lugar,
        LocalDate.of(2023, 5, 20),
        OrigenHecho.DINAMICA);
        String justificacion
            = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ".repeat(10);

    // Act
    SolicitudEliminacion solicitud = visualizador.solicitarEliminarUnHecho(hecho, justificacion);

    // Assert
    assertNotNull(solicitud);
    assertEquals(hecho, solicitud.getHecho());
    assertEquals(visualizador, solicitud.getSolicitante());
    assertEquals(justificacion, solicitud.getJustificacion());

  }


}
