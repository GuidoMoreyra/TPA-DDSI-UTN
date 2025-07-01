package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Conexion;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public final class ConexionFalsa implements Conexion {
  private int contador = 0;

  @Override
  public Map<String, Object> siguienteHecho(URL url, LocalDateTime fechaUltimaConsulta) {
    if (contador >= 3) {
      return null; // Simula que ya no hay más hechos nuevos
    }

    Map<String, Object> datos = new HashMap<>();
    datos.put("titulo", "Hecho #" + contador);
    datos.put("descripcion", "Descripción del hecho " + contador);
    datos.put("categoria", "Categoria X");
    datos.put("contenidoMultimedia", "http://imagen.com/" + contador);
    datos.put("latitud", -34.6 + contador);
    datos.put("longitud", -58.4 - contador);
    datos.put("fecha", LocalDate.now().minusDays(contador));

    contador++;
    return datos;

  }
}
