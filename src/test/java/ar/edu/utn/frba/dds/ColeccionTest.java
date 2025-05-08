package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.usuario.models.Administrador;
import ar.edu.utn.frba.dds.usuario.models.Visualizador;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class ColeccionTest {
  @Test
  public void SecreaUnaColeccionSinCriterioDespuesSeAgregan(){
    Fuente fuenteTest = new FuenteEstatica("formatoTp.csv");
    Coleccion coleccionUnCriterio = new Coleccion("Deportivos Argentina",fuenteTest,null);
    CriterioCategoria criterioCategoria = new CriterioCategoria("deportivo");
    coleccionUnCriterio.agregarCriterio(criterioCategoria);
    coleccionUnCriterio.mostrarColeccion();
  }

  @Test
  public void AdministradorCreaUnaColeccion(){
    Administrador admin = new Administrador(163205,"Fernando","Rossi",27);
    Fuente fuente = new FuenteEstatica("formatoTp.csv");
    List<Criterio> criterios = new ArrayList<>();
    criterios.add(new CriterioCategoria("Incendio Forestal"));
    Coleccion coleccion = admin.crearColeccion("Hechos de incendios", fuente, criterios);
    coleccion.mostrarColeccion(); // Muestra los hechos filtrados según los criterios.

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


  }
}
