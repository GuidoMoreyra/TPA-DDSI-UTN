package ar.edu.utn.frba.dds;

public class SolicitudEliminacion {
  private EstadoSolicitudEliminacion estado;
  private Hecho hecho;
  private Visualizador visualizador;
  private String justificacion;

  ///CONSTRUCTOR///

  public SolicitudEliminacion
      (EstadoSolicitudEliminacion estado,
       Hecho hecho, Visualizador visualizador, String justificacion) {
    this.estado = estado;
    this.hecho = hecho;
    this.visualizador = visualizador;
    this.justificacion = justificacion;
  }

  ///METODOS///

  public void aprobar() {
    this.estado = EstadoSolicitudEliminacion.APROBADO;
  }

  public void rechazar() {
    this.estado = EstadoSolicitudEliminacion.RECHAZADO;
  }

  ///GETTERS///

  public Hecho getHecho() {
    return hecho;
  }
}
