package ar.edu.utn.frba.dds.usuario.models;

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


}
