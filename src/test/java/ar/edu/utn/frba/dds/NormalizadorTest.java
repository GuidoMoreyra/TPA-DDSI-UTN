package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.utils.NormalizadorCsv;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NormalizadorTest {

  @TempDir
  Path tempDir;

  @Test
  @DisplayName("Normaliza y reemplaza correctamente un archivo CSV")
  public void testNormalizaYReemplazaCsv() throws Exception {
    // Crear archivo de entrada temporal
    File csvOriginal = tempDir.resolve("hechos.csv").toFile();
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvOriginal))) {
      writer.write("titulo,descripcion,categoria,latitud,longitud,fechaDelHecho\n");
      writer.write("Robo,  robo en el banco ,   inseguridad , -34.6, -58.4, 2024-05-01\n");
    }

    NormalizadorCsv normalizador = new NormalizadorCsv();
    normalizador.normalizarCsv(csvOriginal);

    // Leer contenido y verificar que fue normalizado

    List<String> lineas = Files.readAllLines(csvOriginal.toPath());


    assertEquals(2, lineas.size()); // encabezado + 1 línea

      String contenido = lineas.get(1);
      assertTrue(contenido.contains("ROBO EN EL BANCO") || contenido.contains("INSEGURIDAD")); // upper + trim
    }


  @Test
  @DisplayName("Lanza una excepción si el archivo no existe")
  public void testArchivoInexistente() {
    File archivoInexistente = new File("no_existe.csv");
    NormalizadorCsv normalizador = new NormalizadorCsv();

    Exception exception = assertThrows(Exception.class, () -> {
      normalizador.normalizarCsv(archivoInexistente);
    });

    assertTrue(exception instanceof FileNotFoundException);
  }
}

