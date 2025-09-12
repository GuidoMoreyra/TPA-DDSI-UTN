package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
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
        "foto.png"
    );
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

    //primero persisto el hecho
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
    SolicitudEliminacion solicitud = new SolicitudEliminacion(hecho, justificacion);


    repoSolicitudesEliminacion.agregarSolicitud(solicitud);
    repoSolicitudesEliminacion.getSolicitudes();

    assertEquals(1, repoSolicitudesEliminacion.getSolicitudes().size());

  }

}
