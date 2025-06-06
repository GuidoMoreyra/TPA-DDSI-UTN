package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.utils.HttpNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

public class FuenteMetaMapa {
  private final Logger logger = Logger.getLogger(FuenteMetaMapa.class.getName());
  private final String rutaApi;
  private static FuenteMetaMapa instancia = null;

  private FuenteMetaMapa() {
    rutaApi = "link de la api";
    instancia = this ;
  }

  public FuenteMetaMapa getInstancia() {
    if(instancia == null) {
      return new FuenteMetaMapa();
    }
    return instancia;
  }


  //Obtenemos los hechos sin filtrar , se podria mejorar haciendo  que los filtros
  // se apliquen directamente desde la fuente y se entreguen filtrados en la coleccion
  public String obtenerHechos() {
    String url = rutaApi + "/hechos";

    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(url))
            .GET()
            .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      int status = response.statusCode();

      if (status == 404) {
        throw new HttpNotFoundException("Recurso no encontrado en: " + url);
      } else if (status >= 400) {
        throw new RuntimeException("Error HTTP " + status + " al acceder a: " + url);
      }

      return response.body();

    } catch (IOException e) {
      logger.severe("IO error al acceder a la API: " + e.getMessage());
    } catch (InterruptedException e) {
      logger.warning("La solicitud fue interrumpida.");
      Thread.currentThread().interrupt();
    } catch (HttpNotFoundException e) {
      logger.warning("[404 NOT FOUND] " + e.getMessage());
    }
    return null;
  }


}
