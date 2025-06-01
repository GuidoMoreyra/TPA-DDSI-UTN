package ar.edu.utn.frba.dds;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ar.edu.utn.frba.dds.models.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteEstatica;
import org.junit.jupiter.api.Test;

public class FuenteEstaticaTest {

    @Test
    public void mostarArchivoCsv() {
      FuenteEstatica fuente = new FuenteEstatica("formatoTp.csv");
      //fuente.mostrarHechos();
      assertNotNull(fuente);
    }

    @Test
    public void muestraHechosFiltrados() {
      FuenteEstatica fuente = new FuenteEstatica("formatoTp.csv");

      CriterioCategoria criterioCategoria = new CriterioCategoria("deportivo");
      //fuente.mostrarHechosQueCumplen(criterioCategoria);
    }



}
