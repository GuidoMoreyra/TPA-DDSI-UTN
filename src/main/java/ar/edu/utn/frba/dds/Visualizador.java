package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;

import static ar.edu.utn.frba.dds.EstadoSolicitudEliminacion.PENDIENTE;

public class Visualizador {

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

  public void subirHecho(Hecho hecho) {
    //TODO
  };

  public void solicitarEliminarUnHecho(Hecho hecho, String justificacion) {
    SolicitudEliminacion solicitud =
        new SolicitudEliminacion(PENDIENTE, hecho,this , justificacion);
  }
}
