package ar.edu.utn.frba.dds.hecho.generators;

import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosFormularioDTO;
import ar.edu.utn.frba.dds.hecho.models.Hecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GeneradorHechoFormularioTest {

    @Test
    public void retornaUnHechoValido() {
        // Arrange
        String titulo = "Título de prueba";
        String descripcion = "Descripción de prueba";
        String categoria = "Categoría de prueba";
        String contenidoMultimedia = "URL de prueba";
        String lugar = "Lugar de prueba";
        LocalDate fechaAcontecimiento = LocalDate.of(2023, 1, 1);
        
        DatosFormularioDTO datosFormulario = new DatosFormularioDTO(
            titulo, descripcion, categoria, contenidoMultimedia, lugar, fechaAcontecimiento
        );
        
        GeneradorHechoFormulario generador = new GeneradorHechoFormulario(datosFormulario);

        // Act
        Hecho hecho = generador.generarHecho();

        // Assert
        assertNotNull(hecho, "El método generarHecho() no debería retornar null");
        assertEquals(titulo, hecho.getTitulo(), "El título del hecho debería coincidir con el proporcionado");
        assertEquals(descripcion, hecho.getDescripcion(), "La descripción del hecho debería coincidir con la proporcionada");
        assertEquals(categoria, hecho.getCategoria(), "La categoría del hecho debería coincidir con la proporcionada");
        assertEquals(fechaAcontecimiento, hecho.getFechaDelHecho(), "La fecha del hecho debería coincidir con la proporcionada");
    }
}