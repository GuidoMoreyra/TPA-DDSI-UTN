package ar.edu.utn.frba.dds;

import java.util.List;

public interface FileReader {
  public List<Hecho> leerArchivo(String rutaArchivo);
}
