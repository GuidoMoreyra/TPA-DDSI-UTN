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
        null);

    hechoDos = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 25),
        OrigenHecho.ESTATICO,
        null);

    hechoTres = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 30),
        OrigenHecho.ESTATICO,
        null);


    hechoUno.setLocalidad("esquel");
    hechoDos.setLocalidad("esquel");
    hechoTres.setLocalidad("esquel");


  }

  @BeforeEach
  void limpiarRepositorio() {
    HechosRepository.getInstance().limpiar();
    assertEquals(0, HechosRepository.getInstance().getHechos().size(), "Repositorio no limpio antes del test");
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

  @Test
  @DisplayName("Devuelve los hechos consensuados que cumplen los criterios creacionales")
  void testObtenerHechosConsensuados() {



    Fuente fuenteMockUno = mock(Fuente.class);
    when(fuenteMockUno.obtenerHechos()).thenReturn(List.of(hechoTres,hechoDos,hechoUno));
    Fuente fuenteMockDos = mock(Fuente.class);
    when(fuenteMockDos.obtenerHechos()).thenReturn(List.of(hechoUno));


    // Act
    HechosRepository.getInstance().limpiar();
    //limpio el repo
    EjecutarConsenso ejecutar = new EjecutarConsenso(List.of(fuenteMockUno,fuenteMockDos));
    ejecutar.evaluarVersionDos();

    /*
    System.out.println("Hechos en repo:");
    HechosRepository.getInstance().getHechos().forEach(h ->
        System.out.println(h.getTitulo() + " - " + h.getFechaDelHecho() + " - " + h.getConsensos())
    );*/

    Coleccion coleccion = new Coleccion(
        fuenteMockUno, // no importa la fuente
        "esquel",
        LocalDate.of(2022, 1, 1),
        LocalDate.of(2022, 12, 31),
        "Incendio Forestal",
        TipoDeConsenso.MULTIPLES_MENCIONES
    );


    List<Hecho> resultado = coleccion.aplicarConsenso();

    // Assert
    assertEquals(3, resultado.size());
  }

  @Test
  @DisplayName("Devuelve los hechos consensuados que cumplen criterios extras")
  void testObtenerHechosConsensuadosExtras() {
    Criterio fechatest = new CriterioFecha(LocalDate.of(2022, 10, 20), LocalDate.of(2022, 10, 26));
    Criterio lugartest = new CriterioLugar("esquel");

    Fuente fuenteMockUno = mock(Fuente.class);
    when(fuenteMockUno.obtenerHechos()).thenReturn(List.of(hechoTres,hechoDos,hechoUno));
    Fuente fuenteMockDos = mock(Fuente.class);
    when(fuenteMockDos.obtenerHechos()).thenReturn(List.of(hechoUno));

    Fuente fuenteTest = mock(Fuente.class);
    when(fuenteTest.obtenerHechos()).thenReturn(List.of(hechoDos));

    // Act
    HechosRepository.getInstance().limpiar();
    //limpio el repo
    EjecutarConsenso ejecutar = new EjecutarConsenso(List.of(fuenteMockUno,fuenteMockDos));
    ejecutar.evaluarVersionDos();

    Coleccion coleccion = new Coleccion(
        fuenteTest, // si importa la fuente
        "esquel",
        LocalDate.of(2022, 1, 1),
        LocalDate.of(2022, 12, 31),
        "Incendio Forestal",
        TipoDeConsenso.MULTIPLES_MENCIONES
    );

    List<Criterio> criteriosTest = List.of(lugartest, fechatest);
    List<Hecho> resultado = coleccion.obtenerColeccionConCriteriosExtra(criteriosTest);

    //aca tenemos un problema como le aplico a ambos los criterios extras
    //voy a tener que aplicar un metodo extra

    // Assert
    assertEquals(1, resultado.size());

  }

}
