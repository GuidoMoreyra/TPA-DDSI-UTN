package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import ar.edu.utn.frba.dds.usuario.models.Administrador;
import ar.edu.utn.frba.dds.usuario.models.Visualizador;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class ColeccionTest {
  @Test
  public void SecreaUnaColeccionSinHechosDespuesSeAgregan(){
    Fuente fuenteTest = new FuenteEstatica("formatoTp.csv");
    Coleccion coleccionUnCriterio = new Coleccion("Deportivos Argentina",fuenteTest,null);
    CriterioCategoria criterioCategoria = new CriterioCategoria("deportivo");
    coleccionUnCriterio.agregarCriterio(criterioCategoria);
    //coleccionUnCriterio.mostrarColeccion();

    // Assert
    assertTrue(coleccionUnCriterio.tieneCriterio(criterioCategoria), "La colección tiene el criterio deportivo");

  }

  @Test
  public void AdministradorCreaUnaColeccionImportandoDatosDesdeUnCsv(){
    Administrador admin = new Administrador(163205,"Fernando","Rossi",27);
    Fuente fuente = new FuenteEstatica("formatoTp.csv");
    List<Criterio> criterios = new ArrayList<>();
    criterios.add(new CriterioCategoria("Incendio Forestal"));
    Coleccion coleccion = admin.crearColeccion("Hechos de incendios", fuente, criterios);
    //coleccion.mostrarColeccion(); // Muestra los hechos filtrados según los criterios.

    // Validaciones
    assertNotNull(coleccion, "La colección no debería ser nula");

  }
  @Test
  public void VisualizadorAplicaFiltrosAunaColeccion(){
    Administrador admin = new Administrador(163205,"Fernando","Rossi",27);
    Visualizador visualizador = new Visualizador(1234,"Sofia","Gallardo",28);
    Fuente fuente = new FuenteEstatica("formatoTp.csv");
    List<Criterio> criterios = new ArrayList<>();
    criterios.add(new CriterioCategoria("Incendio Forestal"));
    Criterio criterioFecha = new CriterioFecha("5/1/2017");
    Coleccion coleccion = admin.crearColeccion("Hechos de incendios", fuente, criterios);
    visualizador.filtrarColeccion(coleccion, criterioFecha);

    // Validaciones
    //assertNotNull(hechosFiltrados);


  }
}
