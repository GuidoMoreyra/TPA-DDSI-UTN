package ar.edu.utn.frba.dds;
import org.junit.jupiter.api.Test;

public class FuenteEstaticaTest {


    @Test
    public void muestraHechosFiltrados() {
      FuenteEstatica fuente = new FuenteEstatica("archivoTest.csv");

      // Creando el criterio para filtrar hechos por tipo de lugar
      CriterioLugar criterioLugar = new CriterioLugar("Ruta Provincial");

      // Llamando a mostrarHechosQueCumplen para mostrar los hechos que cumplen con el criterio
      fuente.mostrarHechosQueCumplen(criterioLugar);

    }



}
