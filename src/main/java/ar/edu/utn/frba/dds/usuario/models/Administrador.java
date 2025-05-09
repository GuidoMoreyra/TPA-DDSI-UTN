package ar.edu.utn.frba.dds.usuario.models;

import ar.edu.utn.frba.dds.Coleccion;
import ar.edu.utn.frba.dds.Criterio;
import ar.edu.utn.frba.dds.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.Fuente;
import ar.edu.utn.frba.dds.SolicitudEliminacion;
import ar.edu.utn.frba.dds.usuario.contracts.GestorHechos;
import java.util.List;

public class Administrador implements GestorHechos {
  private Integer legajo;
  private String nombre;
  private String apellido;
  private Integer edad;


  ////CONSTRUCTOR///
  public Administrador(Integer legajo, String nombre, String apellido, Integer edad) {
    this.legajo = legajo;
    this.nombre = nombre;
    this.apellido = apellido;
    this.edad = edad;
  }

  ////METODOS
  public void importarCsv(String ruta){
    //TODO
  }

  public SolicitudEliminacion solicitudesEliminacionPendientes(
      SolicitudEliminacion solicitud, EstadoSolicitudEliminacion estado
  ) {
    //TODO
    return null;
  }

  public void aprobar(SolicitudEliminacion solicitud) {
    solicitud.aprobar(); //se aprueba el pedido, se cambia el estado de la solicitud a APROBADO
    this.eliminarHecho(solicitud); //se cambia el estado del hecho
  }

  public void rechazar(SolicitudEliminacion solicitud) {
    solicitud.rechazar(); //se rechaza el pedido, se cambia el estado de la solicitud a RECHAZADO
  }

  public void eliminarHecho(SolicitudEliminacion solicitud) {
    solicitud.getHecho().desactivar();
  }

  public Coleccion crearColeccion(String titulo, Fuente fuente, List<Criterio> criterios) {
    return new Coleccion(titulo, fuente, criterios);
  }

  @Override
  public void mostrarColeccion(Coleccion coleccion) {
    coleccion.mostrarColeccion();
  }
}
