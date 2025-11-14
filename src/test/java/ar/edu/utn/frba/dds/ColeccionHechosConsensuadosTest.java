package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.EjecutarConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.algoritmos.ConsensoAbsoluto;
import ar.edu.utn.frba.dds.models.algoritmos.MayoriaSimple;
import ar.edu.utn.frba.dds.models.algoritmos.MultiplesMenciones;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class ColeccionHechosConsensuadosTest implements SimplePersistenceTest {
  private HechosRepository repoHechos;
  private Hecho hechoUno;
  private Hecho hechoDos;
  private Hecho hechoTres;
  @BeforeEach
  void setUp() {

    repoHechos = HechosRepository.getInstance();

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

  @BeforeEach
  void limpiarRepositorio() {
    repoHechos.limpiar();
    repoHechos.limpiarBase();
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
    Boolean noestaCurada = false;
    // Ejecutar
    List<Hecho> resultados = coleccion.obtenerColeccionCriteriosCreacional(noestaCurada);

    // Verificar
    assertEquals(2, resultados.size());
  }

  @Test
  void seCreaColeccionConAlgoritmoDeConsenso() {

    Fuente fuenteMock = mock(Fuente.class);
    AlgoritmoDeConsenso consensoAbsoluto = new ConsensoAbsoluto(List.of(fuenteMock));
    AlgoritmoDeConsenso mayoriaSimple = new MayoriaSimple(List.of(fuenteMock));

    Hecho hechoTestDos = new Hecho("incendio forestal", "desc", "INSEGURIDAD",
        -38, -56, LocalDate.of(2025, 3, 10), OrigenHecho.ESTATICO, null, null);
    hechoTestDos.setLocalidad("Avellaneda");
    hechoTestDos.setAlgoritmos(Set.of(consensoAbsoluto));

    Hecho hechoTestUno = new Hecho("incendio forestal", "desc", "INSEGURIDAD",
        -38, -56, LocalDate.of(2025, 3, 12), OrigenHecho.ESTATICO, null, null);
    hechoTestUno.setLocalidad("Avellaneda");
    hechoTestUno.setAlgoritmos(Set.of(mayoriaSimple));

    repoHechos.agregarHecho(hechoTestUno);
    repoHechos.agregarHecho(hechoTestDos);


    Coordenada coordenadaMock = mock(Coordenada.class);
    when(coordenadaMock.getLocalidad()).thenReturn("Avellaneda");

    // Fuente que devuelve ese hecho
    Fuente fuenteMockCuatro = mock(Fuente.class);
    when(fuenteMockCuatro.obtenerHechos()).thenReturn(List.of(hechoTestDos, hechoTestUno));

    // Crear colección CON algoritmo (no se va a usar en este test)
    Coleccion coleccion = new Coleccion(
        fuenteMockCuatro,
        "Avellaneda",
        LocalDate.of(2025, 1, 1),
        LocalDate.of(2025, 12, 31),
        "INSEGURIDAD",
        consensoAbsoluto
    );

    Boolean estaCurada = true;

    // Ejecutar
    List<Hecho> resultados = coleccion.obtenerColeccionCriteriosCreacional(estaCurada);

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

    Fuente fuenteMocktres = mock(Fuente.class);
    when(fuenteMocktres.obtenerHechos()).thenReturn(List.of(hechoUno));


    //se persisten los hechos
    repoHechos.agregarHecho(hechoUno);
    repoHechos.agregarHecho(hechoDos);
    repoHechos.agregarHecho(hechoTres);

    var fuentesActivas = List.of(fuenteMockUno,fuenteMockDos);

    var algoritmos = List.of(
        new ConsensoAbsoluto(fuentesActivas),
        new MayoriaSimple(fuentesActivas),
        new MultiplesMenciones()
    );

    EjecutarConsenso ejecutar = new EjecutarConsenso();
    ejecutar.aplicarConsensovdos(fuentesActivas, algoritmos);

    AlgoritmoDeConsenso multiplesMenciones = new MultiplesMenciones();

    Coleccion coleccion = new Coleccion(
        fuenteMockUno, // no importa la fuente
        "esquel",
        LocalDate.of(2022, 1, 1),
        LocalDate.of(2022, 12, 31),
        "Incendio Forestal",
        multiplesMenciones
    );

    Boolean estaCurada = true;

    List<Hecho> resultado = coleccion.obtenerColeccionCriteriosCreacional(estaCurada);

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
    var fuentesActivas = List.of(fuenteMockUno,fuenteMockDos);

    var algoritmos = List.of(
        new ConsensoAbsoluto(fuentesActivas),
        new MayoriaSimple(fuentesActivas),
        new MultiplesMenciones()
    );

    //se persisten los hechos

      repoHechos.agregarHecho(hechoUno);
      repoHechos.agregarHecho(hechoDos);
      repoHechos.agregarHecho(hechoTres);

    EjecutarConsenso ejecutar = new EjecutarConsenso();
    ejecutar.aplicarConsensovdos(fuentesActivas, algoritmos);

    AlgoritmoDeConsenso multiplesMenciones = new MultiplesMenciones();

    Coleccion coleccion = new Coleccion(
        fuenteTest, // si importa la fuente
        "esquel",
        LocalDate.of(2022, 1, 1),
        LocalDate.of(2022, 12, 31),
        "Incendio Forestal",
        multiplesMenciones
    );

    List<Criterio> criteriosTest = List.of(lugartest, fechatest);

    Boolean estaCurada = true;
    List<Hecho> resultado = coleccion.obtenerColeccionConCriteriosExtra(criteriosTest, estaCurada);

    //aca tenemos un problema como le aplico a ambos los criterios extras
    //voy a tener que aplicar un metodo extra

    // Assert
    assertEquals(1, resultado.size());

  }

}
