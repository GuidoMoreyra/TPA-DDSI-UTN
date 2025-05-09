package ar.edu.utn.frba.dds.hecho.models.factories;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoAPI;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoCSV;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoFormulario;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosAPIDTO;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosCSVDTO;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosFormularioDTO;
import ar.edu.utn.frba.dds.hecho.models.Hecho;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class GeneradorHechoFactoryTest {

    @Test
    public void origenEstaticoRetornaGeneradorCSV() {
        // Arrange
        DatosCSVDTO datos = new DatosCSVDTO("fila de prueba");

        // Act
        GeneradorHecho generador = GeneradorHechoFactory.crear(OrigenHecho.ESTATICA, datos);

        // Assert
        assertInstanceOf(
                GeneradorHechoCSV.class,
                generador,
                "El generador debería ser una instancia de GeneradorHechoCSV"
        );
    }

    @Test
    public void origenDinamicoRetornaGeneradorFormulario() {
        // Arrange
        DatosFormularioDTO datos = new DatosFormularioDTO(
            "Título", "Descripción", "Categoría", "URL", "Lugar", LocalDate.now()
        );

        // Act
        GeneradorHecho generador = GeneradorHechoFactory.crear(OrigenHecho.DINAMICA, datos);

        // Assert
        assertInstanceOf(
                GeneradorHechoFormulario.class,
                generador,
                "El generador debería ser una instancia de GeneradorHechoFormulario"
        );
    }

    @Test
    public void origenIntermedioRetornaGeneradorAPI() {
        // Arrange
        DatosAPIDTO datos = new DatosAPIDTO(new Object());

        // Act
        GeneradorHecho generador = GeneradorHechoFactory.crear(OrigenHecho.INTERMEDIA, datos);

        // Assert
        assertInstanceOf(
                GeneradorHechoAPI.class,
                generador,
                "El generador debería ser una instancia de GeneradorHechoAPI"
        );
    }
}