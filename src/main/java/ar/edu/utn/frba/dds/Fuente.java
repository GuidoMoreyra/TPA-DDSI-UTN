package ar.edu.utn.frba.dds;

import java.util.Map;

public interface Fuente {
  Iterable<Map<String, String>> leerCsv();
}
