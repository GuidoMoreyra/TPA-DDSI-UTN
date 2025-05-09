package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosCSVDTO;
import ar.edu.utn.frba.dds.hecho.models.Hecho;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class GeneradorHechoCSVTest {

    @Test
    public void retornaUnHechoValido() {
        // Arrange
        String fila = "titulo,descripcion,categoria,lugar,2023-01-01";
        DatosCSVDTO datosCSV = new DatosCSVDTO(fila);
        GeneradorHechoCSV generador = new GeneradorHechoCSV(datosCSV);

        // Act
        Hecho hecho = generador.generarHecho();

        // Assert
        assertNotNull(hecho, "El método generarHecho() no debería retornar null");
        assertEquals("titulo", hecho.getTitulo(), "El título del hecho debería coincidir con el proporcionado");
        assertEquals("descripcion", hecho.getDescripcion(), "La descripción del hecho debería coincidir con la proporcionada");
        assertEquals("categoria", hecho.getCategoria(), "La categoría del hecho debería coincidir con la proporcionada");
        assertEquals(LocalDate.parse("2023-01-01"), hecho.getFechaDelHecho(), "La fecha del hecho debería coincidir con la proporcionada");
    }

    @Test
    public void fallaSiLaLineaDelCSVNoTieneLaCantidadDeDatosRequeridos() {
        // Arrange
        String fila = "titulo,descripcion,categoria,lugar";
        DatosCSVDTO datosCSV = new DatosCSVDTO(fila);
        GeneradorHechoCSV generador = new GeneradorHechoCSV(datosCSV);

        // Act + Assert
        assertThrows(
                IllegalArgumentException.class,
                generador::generarHecho
        );
    }
}