package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.contracts.Reporte;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class ExportadorCsv {

  public ExportadorCsv() {}

  public void exportarCsvArchivo(String rutaArchivo, Reporte reporte) {
    try {
      File file = new File(rutaArchivo);
      if (file.getParentFile() != null) {
        boolean  ok = file.getParentFile().mkdirs();
        if (!ok && !file.getParentFile().exists()) {
          throw new IOException("No se puede crear los directorios "
              + file.getParentFile());
        }

      }

      try (Writer writer = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(file), StandardCharsets.UTF_8))) {
        writer.write(reporte.generarCsv());
        System.out.println("CSV exportado en: " + rutaArchivo);
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
