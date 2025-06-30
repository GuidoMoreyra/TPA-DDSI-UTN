package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.exceptions.HttpNotFoundException;
import ar.edu.utn.frba.dds.models.Hecho;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class FuenteMetaMapa {

  private final String rutaApi;
  private final HttpClient cliente;

  public FuenteMetaMapa (String rutaApi_ , HttpClient cliente_) {
    rutaApi = rutaApi_;
    cliente = cliente_;
  }




  //Obtenemos los hechos sin filtrar , se podria mejorar haciendo  que los filtros
  // se apliquen directamente desde la fuente y se entreguen filtrados en la coleccion
  public List<Hecho> obtenerHechos() {
    String url = rutaApi + "/hechos";

    try {
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
            .GET()
            .build();

      HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
      int status = response.statusCode();

      if (status == 404) {
        throw new HttpNotFoundException("Recurso no encontrado en: " + url);
      } else if (status >= 400) {
        throw new RuntimeException("Error HTTP " + status + " al acceder a: " + url);
      }

      ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.body(), new TypeReference<List<Hecho>>() {});

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }


}
