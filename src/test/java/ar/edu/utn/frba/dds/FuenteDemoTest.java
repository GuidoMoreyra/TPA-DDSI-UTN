package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import ar.edu.utn.frba.dds.exceptions.InvalidoUrlExeception;
import ar.edu.utn.frba.dds.exceptions.UltimaConsultaException;
import ar.edu.utn.frba.dds.models.Conexion;
import ar.edu.utn.frba.dds.repositories.fuentes.AdaptadorFuenteDemo;
import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FuenteDemoTest {

  private Conexion conexionSinResultados;
  private Conexion conexionConResultados;

  @BeforeEach
  void conexionMockSinResultados() {
    conexionSinResultados = mock(Conexion.class);
    // simular una respuesta con null para solo probar la construcción
    when(conexionSinResultados.siguienteHecho(any(URL.class), any(LocalDateTime.class)))
        .thenReturn(null);
  }

  @BeforeEach
  void conexionMockParaobtenerHechos(){
    Map<String, Object> unHecho = new HashMap<>();
    unHecho.put("titulo", "Titulo demo");
    unHecho.put("descripcion", "Descripción demo");
    unHecho.put("categoria", "Evento");
    unHecho.put("contenidoMultimedia", "http://imagen.png");
    unHecho.put("latitud", -34.6037);
    unHecho.put("longitud", -58.3816);
    unHecho.put("fecha", LocalDate.of(2023, 5, 15));

    conexionConResultados = mock(Conexion.class);
    when(conexionConResultados.siguienteHecho(any(URL.class), any(LocalDateTime.class)))
        .thenReturn(unHecho)
        .thenReturn(null);

  }

  @Test
  @DisplayName("Se puede crear un adapter de fuente demo con todos los parametros correctos")
  void crearFuenteDemo(){

    String url = "http://demotest.org/api";
    LocalDateTime ultimaConsultaTest = LocalDateTime.now().minusHours(1);
    //Duration duracion = Duration.ofSeconds(30);
    AdaptadorFuenteDemo adaptador = new AdaptadorFuenteDemo(conexionSinResultados, url,
        ultimaConsultaTest);

    assertNotNull(adaptador);
  }

  @Test
  @DisplayName("No se puede crear un adapter de fuente demo sin una ultimaConsulta")
  void crearFuenteDemoSinUnaUltimaConsulta(){
    String url = "http://demotest.org/api";
    //Duration duracion = Duration.ofSeconds(30);

    Assertions.assertThrows(UltimaConsultaException.class, ()->
        new AdaptadorFuenteDemo(conexionSinResultados, url,
            null));

  }

  @Test
  @DisplayName("no se puede crear un adapter de fuente demo sin una url valida")
  void crearFuenteDemoSinUnaUrlValida(){
    String url = "";
    LocalDateTime ultimaConsultaTest = LocalDateTime.now().minusHours(1);
    //Duration duracion = Duration.ofSeconds(30);

    Assertions.assertThrows(InvalidoUrlExeception.class, ()->
        new AdaptadorFuenteDemo(conexionSinResultados, url,
            ultimaConsultaTest));
  }

  @Test
  @DisplayName("se obtiene un hecho de una fuente demo")
  void obtenerHechoDeUnaFuenteDemo(){
    String url = "http://demotest.org/api";
    LocalDateTime ultimaConsultaTest = LocalDateTime.now().minusHours(1);
    //Duration duracion = Duration.ofSeconds(30);

    AdaptadorFuenteDemo adapterTest = new AdaptadorFuenteDemo(
        conexionConResultados, url, ultimaConsultaTest);

    Assertions.assertEquals(1,adapterTest.obtenerHechos().size());
  }

  @Test
  @DisplayName("No se obtiene hechos de una fuente demo")
  void noseObtieneHechosDeUnaFuenteDemo(){
    String url = "http://demotest.org/api";
    LocalDateTime ultimaConsultaTest = LocalDateTime.now().minusHours(1);
    //Duration duracion = Duration.ofSeconds(30);

    AdaptadorFuenteDemo adapterTest = new AdaptadorFuenteDemo(
      conexionSinResultados, url, ultimaConsultaTest
    );
    Assertions.assertEquals(0,adapterTest.obtenerHechos().size());
  }




}


