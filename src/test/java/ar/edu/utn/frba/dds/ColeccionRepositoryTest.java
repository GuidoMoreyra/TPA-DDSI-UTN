package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import io.github.flbulgarelli.jpa.extras.test.SimplePersistenceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class ColeccionRepositoryTest implements SimplePersistenceTest {
  @Test
  public void obtenerColecciones() {
    entityManager().persist(new Coleccion(
        null,
        null,
        LocalDate.of(2025,1,1),
        LocalDate.of(2025,4,20),
        "Inseguridad",
        TipoDeConsenso.CONSENSO_ABSOLUTO
    ));
    entityManager().persist(new Coleccion(

        null,
        null,
        LocalDate.of(2025,1,1),
        LocalDate.of(2025,12,31),
        "Incendio_forestal",
        TipoDeConsenso.CONSENSO_ABSOLUTO

    ));

    ColeccionRepository repoColeccion = new ColeccionRepository();

    Assertions.assertEquals(1,repoColeccion.deCategoria("Inseguridad").size());
  }

}
