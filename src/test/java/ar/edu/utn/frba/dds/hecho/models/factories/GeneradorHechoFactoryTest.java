package ar.edu.utn.frba.dds.hecho.models.factories;

import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoApi;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoCsv;
import ar.edu.utn.frba.dds.hecho.generators.GeneradorHechoFormulario;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosApiDto;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosCsvDto;
import ar.edu.utn.frba.dds.hecho.generators.dto.DatosFormularioDto;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class GeneradorHechoFactoryTest {

    @Test
    public void origenEstaticoRetornaGeneradorCSV() {
        // Arrange
        DatosCsvDto datos = new DatosCsvDto("fila de prueba");

        // Act
        GeneradorHecho generador = GeneradorHechoFactory.crear(OrigenHecho.ESTATICA, datos);

        // Assert
        assertInstanceOf(
                GeneradorHechoCsv.class,
                generador,
                "El generador debería ser una instancia de GeneradorHechoCSV"
        );
    }

    @Test
    public void origenDinamicoRetornaGeneradorFormulario() {
        // Arrange
        DatosFormularioDto datos = new DatosFormularioDto(
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
        DatosApiDto datos = new DatosApiDto(new Object());

        // Act
        GeneradorHecho generador = GeneradorHechoFactory.crear(OrigenHecho.INTERMEDIA, datos);

        // Assert
        assertInstanceOf(
                GeneradorHechoApi.class,
                generador,
                "El generador debería ser una instancia de GeneradorHechoAPI"
        );
    }
}