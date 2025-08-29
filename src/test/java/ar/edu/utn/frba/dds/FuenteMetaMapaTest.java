package ar.edu.utn.frba.dds;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteMetaMapa;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;


public class FuenteMetaMapaTest {
  @Test
  void testObtenerHechos() throws IOException, InterruptedException {
    HttpClient clienteHttp = mock(HttpClient.class);
    HttpResponse<String> respuestaHttp = mock(HttpResponse.class);

    String json =
        """
                [
              {
                "titulo": "Test",
                "descripcion": "desc",
                "categoria": "A",
                "contenidoMultimedia": null,
                "coordenadas": {
                  "latitud": 1.0,
                  "longitud": 2.0
                },
                "fechaDelHecho": "2023-10-01",
                "fechaCreacion": null,
                "origen": "INTERMEDIO",
                "algoritmos": ["CONSENSO_ABSOLUTO"]
              }
            ]
        """;
    when(respuestaHttp.statusCode()).thenReturn(200);
    when(respuestaHttp.body()).thenReturn(json);
    when(clienteHttp.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
        .thenReturn(respuestaHttp);

    FuenteMetaMapa fuente = new FuenteMetaMapa("http://fake-api.com", clienteHttp);

    List<Hecho> hechos = fuente.obtenerHechos();

    assertEquals(1, hechos.size());
    assertEquals("Test", hechos.get(0).getTitulo());

  }


}
