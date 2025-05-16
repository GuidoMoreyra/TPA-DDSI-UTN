package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ColeccionTest {
  @Test
  public void SecreaUnaColeccionDondeSoloUnHechoCumpleElCriterio(){
    Fuente fuenteTest = new FuenteEstatica("formatoTp.csv");
    Coleccion coleccionUnCriterio = new Coleccion("Deporte Argentino",fuenteTest,null);
    CriterioCategoria criterioCategoria = new CriterioCategoria("deportivo");
    coleccionUnCriterio.agregarCriterio(criterioCategoria);
    coleccionUnCriterio.cargarHechos();

    // Assert
    assertEquals(coleccionUnCriterio.getHechos().size(),1);

  }

  @Test
  public void SeCreaUnacoleccionConVariosHechosCumpliendoElCriterio(){
    Fuente fuenteTest = new FuenteEstatica("formatoTp.csv");

    List<Criterio> criteriosForestal = new ArrayList<>();
    criteriosForestal.add(new CriterioCategoria("Incendio Forestal"));
    Coleccion coleccionConVarioshechos = new Coleccion("Incendio Forestal",fuenteTest,criteriosForestal);
    coleccionConVarioshechos.cargarHechos();
     assertEquals(coleccionConVarioshechos.getHechos().size(),4);



    // Validaciones


  }
  @Test
  public void VisualizadorAplicaFiltrosAunaColeccion(){
    Fuente fuente = new FuenteEstatica("formatoTp.csv");
    List<Criterio> criterios = new ArrayList<>();
    criterios.add(new CriterioCategoria("Incendio Forestal"));
    Coleccion coleccion = new Coleccion("Incendio Forestal",fuente,criterios);
    Criterio criterioFecha = new CriterioFecha("05/01/2017");
    List<Criterio> criteriosVisualizador = new ArrayList<>();
    criteriosVisualizador.add(criterioFecha);
    coleccion.cargarHechos();



    // Validaciones
    assertEquals(coleccion.cargarHechosConFiltros(criteriosVisualizador).size(),2);
    List<Hecho> filtrados = coleccion.cargarHechosConFiltros(criteriosVisualizador);
    assertTrue(filtrados.stream().allMatch(h -> h.getFechaDelHecho().equals(LocalDate.parse("05/01/2017", DateTimeFormatter.ofPattern("dd/MM/yyyy")))));


  }
}
