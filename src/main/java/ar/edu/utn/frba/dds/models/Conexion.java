package ar.edu.utn.frba.dds.models;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Map;

public interface  Conexion {
  /**
   * Devuelve un mapa con los atributos de un hecho, indexados por nombre de
   * atributo. Si el método retorna null, significa que no hay nuevos hechos
   * por ahora. La fecha es opcional
   */
  public Map<String, Object> siguienteHecho(URL url, LocalDateTime fechaUltimaConsulta);
}
