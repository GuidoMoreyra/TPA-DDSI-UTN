package ar.edu.utn.frba.dds.usuario.models;

import ar.edu.utn.frba.dds.Coleccion;
import ar.edu.utn.frba.dds.Criterio;
import ar.edu.utn.frba.dds.usuario.contracts.GestorHechos;

public class Visualizador implements GestorHechos {

  private Integer id;
  private String nombre;
  private String apellido;
  private Integer edad;

  ////CONSTRUCTOR
  public Visualizador(Integer id, String nombre, String apellido, Integer edad) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
  }

  @Override
  public void mostrarColeccion(Coleccion coleccion) {
    coleccion.mostrarColeccion();
  }

  public void filtrarColeccion(Coleccion coleccion, Criterio criterio) {
    // Aplica el filtro sobre la colección
    coleccion.agregarCriterio(criterio);  // Agregar el nuevo criterio de filtro a la colección
    coleccion.mostrarColeccion();  // Muestra la colección filtrada
  }
}
