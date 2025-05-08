package ar.edu.utn.frba.dds;
import org.junit.jupiter.api.Test;

public class FuenteEstaticaTest {

    @Test
    public void mostarArchivoCsv() {
      FuenteEstatica fuente = new FuenteEstatica("formatoTp.csv");
      //fuente.mostrarHechos();
    }

    @Test
    public void muestraHechosFiltrados() {
      FuenteEstatica fuente = new FuenteEstatica("formatoTp.csv");

      CriterioCategoria criterioCategoria = new CriterioCategoria("deportivo");
      //fuente.mostrarHechosQueCumplen(criterioCategoria);
    }



}
