package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class HechoPersistenciaTest implements SimplePersistenceTest {

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
        "imagen.jpg");

    entityManager().persist(hecho);

    Assertions.assertNotNull(hecho.getId());
  }


}
