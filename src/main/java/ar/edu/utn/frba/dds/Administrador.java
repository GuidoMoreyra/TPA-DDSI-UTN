package ar.edu.utn.frba.dds;

public class Administrador {
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
  public void crearColeccion(){}

  public void subirHecho(){
    //TODO
  }

  public void importarCSV(/*csv*/){
    //TODO
  }

  public SolicitudEliminacion solicitudesEliminacionPendientes(SolicitudEliminacion solicitud, EstadoSolicitudEliminacion estado) {
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



}
