package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteEstatica;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuenteEstaticaTest {

  @Test
  public void fuenteEstaticaDevuelveDiezHechosDesdeArchivo() {
    // se usa un archivo que tiene 10 hechos
    List<Hecho>hechosTest = new ArrayList<>();
    FuenteEstatica fuente = new FuenteEstatica("formatoTP",hechosTest);
    fuente.actualizarHechos();
    fuente.agregarHechos();
    List<Hecho> hechos = fuente.obtenerHechos();

    assertEquals(10, hechos.size());
  }

  @Test
  public void elPrimerHechoDeLaFuenteEstaticaNoEsNulo() {
    List<Hecho>hechosTest = new ArrayList<>();
    FuenteEstatica fuente = new FuenteEstatica("formatoTP",hechosTest);
    fuente.actualizarHechos();
    fuente.agregarHechos();
    List<Hecho> hechos = fuente.obtenerHechos();

    assertNotNull(hechos.get(0));
  }
}
