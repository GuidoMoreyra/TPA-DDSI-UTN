package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.dto.HechoCsvDto;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

// Codigo completamente hecho por chatGPT deberiamos revisarlo

// Esta normalizacion deberia ejecutarse a la hora de cargar el archivo.
// Se lo normaliza a este formato:
//    Título, Descripción, Categoría, Latitud, Longitud, Fecha del hecho

public final class NormalizadorCsv {

  public void normalizarCsv(File csvFile) throws Exception {
    // 1. Leer el CSV original

    List<HechoCsvDto> hechos;

    try (BufferedReader reader =
        new BufferedReader(
            new InputStreamReader(new FileInputStream(csvFile), StandardCharsets.UTF_8))) {
      hechos =
          new CsvToBeanBuilder<HechoCsvDto>(reader)
              .withType(HechoCsvDto.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build()
              .parse();
    }

    // 2. Aplicar transformación / limpieza si hiciera falta
    hechos.forEach(
        hecho -> {
          hecho.setDescripcion(hecho.getDescripcion().trim());

          hecho.setCategoria(hecho.getCategoria().trim().toUpperCase());
        });

    // 3. Sobrescribir el archivo original con los datos estandarizados
    File tempFile = File.createTempFile("normalized-", ".csv");
    try (BufferedWriter writer =
        new BufferedWriter(
            new OutputStreamWriter(new FileOutputStream(tempFile), StandardCharsets.UTF_8)); ) {
      StatefulBeanToCsv<HechoCsvDto> beanToCsv =
          new StatefulBeanToCsvBuilder<HechoCsvDto>(writer).build();
      beanToCsv.write(hechos);
    }

    // 4. Reemplazar el original con el normalizado
    Files.move(tempFile.toPath(), csvFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
  }
}
