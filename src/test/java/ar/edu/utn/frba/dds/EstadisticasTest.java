package ar.edu.utn.frba.dds;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.ComponenteDeEstadisticas;
import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EstadisticasTest implements SimplePersistenceTest {
  Fuente fuenteMock;
  Coordenada coordenadaMock;
  CriterioLugar criterioLugarMock;
  CriterioCategoria criterioCategoriaMock;
  CriterioFecha criterioFechaMock;

  @BeforeEach
  public void setup(){
    fuenteMock = mock(Fuente.class);
    // Criterios de creacion de una coleccion
    criterioLugarMock = mock(CriterioLugar.class);
    criterioCategoriaMock = mock(CriterioCategoria.class);
    criterioFechaMock = mock(CriterioFecha.class);
    // Otros - por el momento sin uso, falta implementar API
    coordenadaMock = mock(Coordenada.class);

  }


  @Test
  @DisplayName("se crea un hecho y se lo persiste ")
  public void creaUnHecho() {
    Hecho hecho = new Hecho(
        "Titulo",
        "Descripcion",
        "Categoria",
        -34.5,
        -58.4,
        LocalDate.of(2024, 1, 1),
        OrigenHecho.ESTATICO,
        "imagen.jpg",null);

    entityManager().persist(hecho);

    Assertions.assertNotNull(hecho.getId());
  }

  @Test
  @DisplayName("se crea un hecho y se busca obtener la hora del hecho")
  public void horaHecho(){

      Hecho hecho = new Hecho(
          "Titulo",
          "Descripcion",
          "Categoria",
          -34.5,
          -58.4,
          LocalDate.of(2024,1,1),
          OrigenHecho.ESTATICO,
          "imagen.jpg",
          LocalTime.of(22,30,1)
      );

      Assertions.assertEquals(
          LocalTime.of(22,30,1),hecho.getHoraHecho());

    }

  @Test
  @DisplayName("se crea un hecho y se obtiene su provincia")
  public void provinciaHecho(){
    Hecho hecho = new Hecho(
        "Titulo",
        "Descripcion",
        "Categoria",
        -34.6,
        -58.4,
        LocalDate.of(2024,1,1),
        OrigenHecho.ESTATICO,
        "imagen.jpg",
        LocalTime.of(22,30,1)
    );

    Assertions.assertEquals(
        Provincia.BUENOS_AIRES,hecho.getProvincia());

  }

  @Test
  @DisplayName("se crea un coleccion y se realiza las estadisticas")
  public void estadisticasColeccion(){
    Hecho hechoUno = new Hecho(
        "Titulo",
        "Descripcion",
        "Inseguridad",
        -34.6,
        -58.4,
        LocalDate.of(2024,1,1),
        OrigenHecho.ESTATICO,
        "imagen.jpg",
        LocalTime.of(22,30,1)
    );

    Hecho hechoDos = new Hecho(
        "Titulo",
        "Descripcion",
        "Deporte",
        -34.6,
        -58.4,
        LocalDate.of(2024,1,1),
        OrigenHecho.ESTATICO,
        "imagen.jpg",
        LocalTime.of(22,30,1)
    );

    Hecho hechoTres = new Hecho(
        "Titulo",
        "Descripcion",
        "Inseguridad",
        -34.6,
        -58.4,
        LocalDate.of(2024,1,1),
        OrigenHecho.ESTATICO,
        "imagen.jpg",
        LocalTime.of(22,0,1)
    );

    Hecho hechoCuatro = new Hecho(
        "Titulo",
        "Descripcion",
        "Siniestro",
        -34.6,
        -58.4,
        LocalDate.of(2024,1,1),
        OrigenHecho.ESTATICO,
        "imagen.jpg",
        LocalTime.of(21,30,1)
    );

    Hecho hechoCinco = new Hecho(
        "Titulo",
        "Descripcion",
        "Inseguridad",
        -34.5,
        -58,
        LocalDate.of(2024,1,1),
        OrigenHecho.ESTATICO,
        "imagen.jpg",
        LocalTime.of(22,15,1)
    );

    when(fuenteMock.obtenerHechos()).thenReturn(List.of(hechoUno, hechoDos, hechoTres, hechoCuatro, hechoCinco));


    LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
    LocalDate fechaFin = LocalDate.of(2024, 12, 31);

    Coleccion coleccion = new Coleccion(
        fuenteMock,
        "Buenos Aires",
        fechaInicio,
        fechaFin,
        "Inseguridad",
        null);

    coleccion.setEstaCurada(false);
    coleccion.calcularHoraPico();
    coleccion.calcularProvinciaConMasHechos();
    coleccion.calcularHechosReportados();


    Assertions.assertEquals(22,coleccion.getHoraPicoHechos());
    Assertions.assertEquals(Provincia.BUENOS_AIRES,coleccion.getProvinciaConMasHechos());
    Assertions.assertEquals(3,coleccion.getCantidadHechosReportados());
    Assertions.assertEquals("Inseguridad",coleccion.getCategoria());

  }

  @Test
  @DisplayName("se obtiene un reporte usando el componente de estadisticas")
  public void estadisticasComponenteDeEstadisticas(){

    Coleccion coleccionUno = mock(Coleccion.class);
    Coleccion coleccionDos = mock(Coleccion.class);
    Coleccion coleccionTres = mock(Coleccion.class);
    Coleccion coleccionCuatro = mock(Coleccion.class);

    when(coleccionUno.getCategoria()).thenReturn("Inseguridad");
    when(coleccionDos.getCategoria()).thenReturn("Inseguridad");
    when(coleccionTres.getCategoria()).thenReturn("Deporte");
    when(coleccionCuatro.getCategoria()).thenReturn("Sinientro");

    when(coleccionUno.getHoraPicoHechos()).thenReturn(22);
    when(coleccionDos.getHoraPicoHechos()).thenReturn(12);
    when(coleccionTres.getHoraPicoHechos()).thenReturn(17);
    when(coleccionCuatro.getHoraPicoHechos()).thenReturn(3);

    when(coleccionUno.getProvinciaConMasHechos()).thenReturn(Provincia.RIO_NEGRO);
    when(coleccionDos.getProvinciaConMasHechos()).thenReturn(Provincia.RIO_NEGRO);
    when(coleccionTres.getProvinciaConMasHechos()).thenReturn(Provincia.RIO_NEGRO);
    when(coleccionCuatro.getProvinciaConMasHechos()).thenReturn(Provincia.RIO_NEGRO);

    when(coleccionUno.getCantidadHechosReportados()).thenReturn(3);
    when(coleccionDos.getCantidadHechosReportados()).thenReturn(2);
    when(coleccionTres.getCantidadHechosReportados()).thenReturn(2);
    when(coleccionCuatro.getCantidadHechosReportados()).thenReturn(10);


    //  Mockeo del repositorio
    ColeccionRepository repoMock = mock(ColeccionRepository.class);
    when(repoMock.listar()).thenReturn(List.of(coleccionUno, coleccionDos, coleccionTres));

    // Mockeo de repo de solicitudes
    SolicitudesEliminacionRepository repoSolicMock = mock(SolicitudesEliminacionRepository.class);
    when(repoSolicMock.getSolicitudes()).thenReturn(List.of());
    when(repoSolicMock.cantidadDeSolicitudesSpam(anyList())).thenReturn(0L);


    ComponenteDeEstadisticas componente = new ComponenteDeEstadisticas(
        repoMock,
        repoSolicMock,
        "Inseguridad"
    );


    componente.actualizar();


    Assertions.assertEquals("Inseguridad", componente.getCategoriaConMasHechos());
    Assertions.assertEquals(Provincia.RIO_NEGRO, componente.getProvinciaConMasHechosPorCategoria());
    Assertions.assertEquals(Provincia.RIO_NEGRO, componente.getProvinciaConMasHechos());
    Assertions.assertEquals(22, componente.getHoraDePicoSegunCategoria());








  }



}
