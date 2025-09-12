package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.EjecutarConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

public class ColeccionHechosConsensuadosTest {
  private Hecho hechoUno;
  private Hecho hechoDos;
  private Hecho hechoTres;
  @BeforeEach
  void setUp() {

    hechoUno = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 29),
        OrigenHecho.ESTATICO,
        null,
        null);

    hechoDos = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 25),
        OrigenHecho.ESTATICO,
        null,null);

    hechoTres = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 30),
        OrigenHecho.ESTATICO,
        null,null);


    hechoUno.setLocalidad("esquel");
    hechoDos.setLocalidad("esquel");
    hechoTres.setLocalidad("esquel");


  }

  @Test
  void testSeCreaColeccionSinAlgoritmoDeConsenso() {
    Coordenada coordenadaMock = mock(Coordenada.class);
    when(coordenadaMock.getLocalidad()).thenReturn("Quilmes");
    Hecho hechoMockUno = mock(Hecho.class);
    when(hechoMockUno.getCategoria()).thenReturn("INSEGURIDAD");
    when(hechoMockUno.getCoordenadas()).thenReturn(coordenadaMock);
    when(hechoMockUno.getFechaDelHecho()).thenReturn(LocalDate.of(2024, 5, 15));

    Hecho hechoMockDos = mock(Hecho.class);
    when(hechoMockDos.getCategoria()).thenReturn("INSEGURIDAD");
    when(hechoMockDos.getCoordenadas()).thenReturn(coordenadaMock);
    when(hechoMockDos.getFechaDelHecho()).thenReturn(LocalDate.of(2024, 6, 22));

    when(hechoMockUno.estaActivo()).thenReturn(true);
    when(hechoMockDos.estaActivo()).thenReturn(true);

    // Mock de Fuente
    Fuente fuenteMockTres = mock(Fuente.class);
    when(fuenteMockTres.obtenerHechos()).thenReturn(List.of(hechoMockUno, hechoMockDos));



    // Crear colección sin algoritmo de consenso
    Coleccion coleccion = new Coleccion(
        fuenteMockTres,
        "Quilmes",
        LocalDate.of(2024, 5, 1),
        LocalDate.of(2024, 9, 30),
        "INSEGURIDAD",
        null
    );

    // Ejecutar
    List<Hecho> resultados = coleccion.obtenerColeccion();

    // Verificar
    assertEquals(2, resultados.size());
  }

  @Test
  void seCreaColeccionConAlgoritmoDeConsenso() {
    Coordenada coordenadaMock = mock(Coordenada.class);
    when(coordenadaMock.getLocalidad()).thenReturn("Avellaneda");

    Hecho hechoMockUno = mock(Hecho.class);
    when(hechoMockUno.getCategoria()).thenReturn("INSEGURIDAD");
    when(hechoMockUno.getCoordenadas()).thenReturn(coordenadaMock);
    when(hechoMockUno.getFechaDelHecho()).thenReturn(LocalDate.of(2025, 3, 10));
    when(hechoMockUno.estaActivo()).thenReturn(true);

    Hecho hechoMockDos = mock(Hecho.class);
    when(hechoMockDos.getCategoria()).thenReturn("INSEGURIDAD");
    when(hechoMockDos.getCoordenadas()).thenReturn(coordenadaMock);
    when(hechoMockDos.getFechaDelHecho()).thenReturn(LocalDate.of(2025, 3, 12));
    when(hechoMockDos.estaActivo()).thenReturn(true);

    // Fuente que devuelve ese hecho
    Fuente fuenteMockCuatro = mock(Fuente.class);
    when(fuenteMockCuatro.obtenerHechos()).thenReturn(List.of(hechoMockUno, hechoMockDos));


    // Crear colección CON algoritmo (no se va a usar en este test)
    Coleccion coleccion = new Coleccion(
        fuenteMockCuatro,
        "Avellaneda",
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2025, 12, 31),
        "INSEGURIDAD",
        TipoDeConsenso.MAYORIA_SIMPLE
    );

    // Ejecutar
    List<Hecho> resultados = coleccion.obtenerColeccion();

    // Verificar
    assertEquals(2, resultados.size());

  }


}
