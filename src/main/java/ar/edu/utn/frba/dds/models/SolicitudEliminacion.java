package ar.edu.utn.frba.dds.models;

public class SolicitudEliminacion {
  private Hecho hecho;
  private String justificacion;
  private int id;

  ///CONSTRUCTOR///

  public SolicitudEliminacion(
       int id,
       Hecho hecho,
       String justificacion
  ) {
    this.hecho = hecho;
    this.id = id;
    if (justificacion.length() < 500) {
      throw new IllegalArgumentException(
        "La justificacion de una solicitud de eliminación no puede ser menor a 500 caracteres"
      );
    }
    this.justificacion = justificacion;
  }

  ///GETTERS///

  public Hecho getHecho() {
    return hecho;
  }

  public String getJustificacion() {
    return justificacion;
  }

  public int getId() {
    return id;
  }

}
