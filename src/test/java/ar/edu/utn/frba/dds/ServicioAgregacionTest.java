package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.EjecutarConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.algoritmos.ConsensoAbsoluto;
import ar.edu.utn.frba.dds.models.algoritmos.MayoriaSimple;
import ar.edu.utn.frba.dds.models.algoritmos.MultiplesMenciones;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteDeAgregacion;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

public class ServicioAgregacionTest implements SimplePersistenceTest {
  public FuenteDeAgregacion servicioTest;
  public Fuente fuenteMockUno = mock(Fuente.class);
  public Fuente fuenteMockDos = mock(Fuente.class);
  public  EjecutarConsenso ejecutar;
  private HechosRepository repoHechos;
  public HechosRepository repoTest = mock(HechosRepository.class);


  @BeforeEach
  void setup(){
    repoHechos = HechosRepository.getInstance();

    Hecho hechoUno = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 29),
        OrigenHecho.ESTATICO,
        null,null);

    Hecho hechoDos = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 25),
        OrigenHecho.ESTATICO,
        null,null);

    Hecho hechoTres = new Hecho("incendio forestal esquel",
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

    Hecho hechoCuatro = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 29),
        OrigenHecho.DINAMICO,
        null,null);


    Hecho hechoCinco = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 25),
        OrigenHecho.DINAMICO,
        null,null);

    Hecho hechoSeis = new Hecho("incendio forestal esquel",
        "un campista se olvido apagar correctamente las brazas",
        "Incendio Forestal",
        -38,
        -56,
        LocalDate.of(2022, 10, 30),
        OrigenHecho.DINAMICO,
        null,null);


    hechoCinco.setLocalidad("esquel");
    hechoCuatro.setLocalidad("esquel");
    hechoSeis.setLocalidad("esquel");

    repoHechos.agregarHecho(hechoUno);
    repoHechos.agregarHecho(hechoDos);
    repoHechos.agregarHecho(hechoTres);
    repoHechos.agregarHecho(hechoCuatro);
    repoHechos.agregarHecho(hechoCinco);
    repoHechos.agregarHecho(hechoSeis);

    when(fuenteMockUno.obtenerHechos()).thenReturn(List.of(hechoTres,hechoDos,hechoUno));

    when(fuenteMockDos.obtenerHechos()).thenReturn(List.of(hechoCuatro,hechoCinco,hechoSeis));

    when(repoTest.getHechos()).thenReturn(List.of(hechoTres,hechoDos,hechoUno,hechoCuatro,hechoCinco,hechoSeis));
    // Act

    //HechosRepository.getInstance().limpiar(); //limpio el repo por las dudas
    var fuentesActivas = List.of(fuenteMockUno,fuenteMockDos);

    var algoritmos = List.of(
        new ConsensoAbsoluto(fuentesActivas),
        new MayoriaSimple(fuentesActivas),
        new MultiplesMenciones()
    );

    ejecutar = new EjecutarConsenso(fuentesActivas, algoritmos);
    servicioTest = new FuenteDeAgregacion(List.of(fuenteMockUno,fuenteMockDos));
    List<Hecho> hechos = servicioTest.obtenerHechos();
    ejecutar.evaluarHechos(hechos);
  }

  @Test
  @DisplayName("se utiliza como fuente principal un servicio de agregacion Para crear una coleccion")
  void seCreaColeccionUsandoUnservicioDeAgregacion() {

    Coleccion coleccion = new Coleccion(
        servicioTest,
        "esquel",
        LocalDate.of(2022, 1, 1),
        LocalDate.of(2022, 12, 31),
        "Incendio Forestal",
        TipoDeConsenso.MULTIPLES_MENCIONES
    );

    List<Hecho> resultado = coleccion.obtenerColeccionCriteriosCreacional();

    // Assert
    assertEquals(6, resultado.size());

  }

  @Test
  @DisplayName("se utiliza una fuente de agregacion y para obtener hechos consensuados")
  void hechosConsenuadosConServicioDeAgregacion() {

    Coleccion coleccion = new Coleccion(
        servicioTest,
        "esquel",
        LocalDate.of(2022, 1, 1),
        LocalDate.of(2022, 12, 31),
        "Incendio Forestal",
        TipoDeConsenso.MULTIPLES_MENCIONES
    );

    coleccion.setEstaCurada(true);

    List<Hecho> resultado = coleccion.obtenerColeccionCriteriosCreacional();
    assertEquals(6, resultado.size());

  }
}
