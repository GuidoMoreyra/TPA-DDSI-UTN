package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.utils.EntityManagerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HechoFullTextSearchTest {

  private final HechosRepository hechosRepository = HechosRepository.getInstance();

  private final List<Hecho> hechos = List.of(
      new Hecho("Inundación en Buenos Aires", "Descripción 1", "clima", 0, 0, null, null, null),
      new Hecho("Corte de luz en Córdoba", "Descripción 2", "servicios", 0, 0, null, null, null),
      new Hecho("Manifestación en Rosario", "Descripción 3", "social", 0, 0, null, null, null),
      new Hecho("Titulo 1", "Asalto en CABA", "social", 0, 0, null, null, null),
      new Hecho("Titulo 2", "Robo en licorería", "social", 0, 0, null, null, null),
      new Hecho("Titulo 3", "Incendio forestal", "social", 0, 0, null, null, null)
  );

  @BeforeAll
  public static void setUpOnce() {
    EntityManagerFactory.main(null);
  }

  @ParameterizedTest
  @CsvSource({
      "inundación, true",
      "córdoba, true",
      "rosario, true",
      "mendoza, false",
  })
  void testFullTextSearchEnTitulo(String texto, boolean expected) {
    this.hechos.forEach(this.hechosRepository::agregarHecho);

    boolean textFound = ! this.hechosRepository
        .buscarPorTexto(texto)
        .isEmpty();

    if (expected) {
      assertTrue(textFound);
    } else {
      assertFalse(textFound);
    }
  }

  @ParameterizedTest
  @CsvSource({
      "asalto, true",
      "robo, true",
      "incendio, true",
      "huracán, false",
  })
  void testFullTextSearchEnDescripcion(String texto, boolean expected) {
    this.hechos.forEach(this.hechosRepository::agregarHecho);

    boolean textFound = ! this.hechosRepository
        .buscarPorTexto(texto)
        .isEmpty();

    if (expected) {
      assertTrue(textFound);
    } else {
      assertFalse(textFound);
    }
  }

}
