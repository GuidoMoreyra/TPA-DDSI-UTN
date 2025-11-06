package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.DetectorDeSpamBasico;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteEstatica;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class PersistenceTest implements SimplePersistenceTest {

  private Hecho hecho;
  private HechosRepository repoHechos;
  private SolicitudesAgregacionRepository repoSolicitudesAgregacion;
  private SolicitudesEliminacionRepository repoSolicitudesEliminacion;

  @BeforeEach
  public void setup(){

    repoHechos = HechosRepository.getInstance();
    repoSolicitudesAgregacion = SolicitudesAgregacionRepository.getInstance();
    repoSolicitudesEliminacion = SolicitudesEliminacionRepository.getInstance();

    hecho = new Hecho(
        "Incendio",
        "Fuego en el bosque",
        "Incendio Forestal",
        -34.6,
        -58.4,
        LocalDate.of(2023, 10, 5),
        OrigenHecho.ESTATICO,
        "foto.png",
        LocalTime.of(1,1,1)
    );

    repoHechos.limpiarBase();
  }

 //HechosRepository
  @Test
  public void sePuedePersistirYConsultarUnHecho(){

      repoHechos.agregarHecho(hecho);
      repoHechos.getHechos();

    assertEquals(1, repoHechos.getHechos().size());
  }

  //SolicitudesAgregacionRepository
  @Test
  public void sePuedePersistirYConsultarUnaSolicitudDeAgregacion(){

      repoHechos.agregarHecho(hecho);

    SolicitudAgregacion solicitud = new SolicitudAgregacion(hecho, false);

      repoSolicitudesAgregacion.agregarSolicitud(solicitud);
      repoSolicitudesAgregacion.getSolicitudes();

    assertEquals(1, repoSolicitudesAgregacion.getSolicitudes().size());
   }

  @Test
  public void sePuedePersistirYConsultarUnaSolicitudDeEliminacion(){

      //primero persisto el hecho
      repoHechos.agregarHecho(hecho);

    String justificacion = "a".repeat(501);
    DetectorDeSpamBasico detector = new DetectorDeSpamBasico();
    SolicitudEliminacion solicitud = new SolicitudEliminacion(hecho, justificacion,detector);

    repoSolicitudesEliminacion.agregarSolicitud(solicitud);
    repoSolicitudesEliminacion.getSolicitudes();


    assertEquals(1, repoSolicitudesEliminacion.getSolicitudes().size());

  }

  @Test
  public void seCalculaLaProvicniaConMasHechosSegunCategoria(){

    Hecho hechoUno = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );


    Hecho hechoDos = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    Hecho hechoTres = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    Hecho hechoCuatro = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -55.3,
        -68.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    HechosRepository repo = HechosRepository.getInstance();
    repo.agregarHecho(hechoUno);
    repo.agregarHecho(hechoDos);
    repo.agregarHecho(hechoTres);
    repo.agregarHecho(hechoCuatro);

    String categoriaTest = "Incendio";

    //Assertions.assertEquals(Provincia.CORRIENTES,hechoUno.getProvincia());
    Assertions.assertEquals(Provincia.CORRIENTES, repo.buscarProvinciaConMasHechosPorCategoria(categoriaTest));
    //Assertions.assertEquals(hechoUno.getProvincia(),entityManager().find(Hecho.class, hechoUno.getId()).getProvincia());

  }

  @Test
  public void seCalculaLaHoraPicoDeHechosSegunCategoria(){
    Hecho hechoUno = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );


    Hecho hechoDos = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    Hecho hechoTres = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    Hecho hechoCuatro = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -55.3,
        -68.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    HechosRepository repo = HechosRepository.getInstance();
    repo.agregarHecho(hechoUno);
    repo.agregarHecho(hechoDos);
    repo.agregarHecho(hechoTres);
    repo.agregarHecho(hechoCuatro);

    String categoriaTest = "Incendio";

    //Assertions.assertEquals(Provincia.CORRIENTES,hechoUno.getProvincia());
    Assertions.assertEquals(12, repo.buscarHoraPicoDeHechosSegun(categoriaTest));
    //Assertions.assertEquals(hechoUno.getProvincia(),entityManager().find(Hecho.class, hechoUno.getId()).getProvincia());

  }

  @Test
  public void seCalculaCategoriaConMasHechos(){
    Hecho hechoUno = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );


    Hecho hechoDos = new Hecho(
        "titulo",
        "descripcion",
        "Educacion",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    Hecho hechoTres = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    Hecho hechoCuatro = new Hecho(
        "titulo",
        "descripcion",
        "Siniestro",
        -55.3,
        -68.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    HechosRepository repo = HechosRepository.getInstance();
    repo.agregarHecho(hechoUno);
    repo.agregarHecho(hechoDos);
    repo.agregarHecho(hechoTres);
    repo.agregarHecho(hechoCuatro);


    Assertions.assertEquals("Incendio",repo.buscarCategoriaConMasHechos());
  }

  @Test
  public void seCalculaLasSolicitudesSpam(){
    Hecho hechoUno = new Hecho(
        "titulo",
        "descripcion",
        "Incendio",
        -30.5,
        -55.5,
        LocalDate.of(2024,2,20),
        OrigenHecho.ESTATICO,
        "criteriosTest.url",
        LocalTime.of(12,3,22)
    );

    String justificacion ="a".repeat(501);
    DetectorDeSpamBasico detector = new DetectorDeSpamBasico();
    SolicitudEliminacion solElimUno = new SolicitudEliminacion(
        hechoUno,
        justificacion,
        detector
    );


    HechosRepository repoHecho = HechosRepository.getInstance();
    repoHecho.agregarHecho(hechoUno);

    SolicitudesEliminacionRepository repoSol = SolicitudesEliminacionRepository.getInstance();
    repoSol.agregarSolicitud(solElimUno);

    Assertions.assertTrue(detector.esSpam(justificacion));
    Assertions.assertEquals(1,repoSol.cantidadDeSolicitudesSpamDos());
  }

  @Test
  public void seCalculaLaProvinciaconMasHechosDeUnaColeccion(){
    HechosRepository repoHecho = HechosRepository.getInstance();
    ColeccionRepository coleccionRepository = new ColeccionRepository();
    List<Hecho>hechosTest = new ArrayList<>();
    FuenteEstatica fuenteEstatica = new FuenteEstatica("hechos");

    fuenteEstatica.obtenerHechos()
        .stream().forEach(hecho -> {
          repoHecho.agregarHecho(hecho);
        });


    Coleccion coleccion = new Coleccion(
        fuenteEstatica,
        "buenos aires",
        LocalDate.of(2024,1,1),
        LocalDate.of(2024,12,30),
        "Incendio_Forestal",
        TipoDeConsenso.MAYORIA_SIMPLE

    );
    entityManager().persist(fuenteEstatica);
    coleccion.agregarHechos();

    coleccionRepository.persistir(coleccion);
    Boolean esIrrestricto = false;


    Assertions.assertEquals(Provincia.CORRIENTES,coleccionRepository.provinciaConMasHechos(coleccion.getId()));

  }


}
