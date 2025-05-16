package ar.edu.utn.frba.dds.hecho.models;

//import ar.edu.utn.frba.dds.hecho.dto.HechoLugarDto;
import ar.edu.utn.frba.dds.hecho.enums.EstadoDelHecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import java.time.LocalDate;

public class Hecho {
  private String titulo;
  private String descripcion;
  private String categoria;
  //private String contenidoMultimedia;
  //private HechoLugarDto lugar;
  private Double latitud;
  private Double longitud;
  private LocalDate fechaDelHecho;
  private LocalDate fechaCreacion;
  //private OrigenHecho origen;
  private EstadoDelHecho estado;

  ////CONSTRUCTOR///

  public Hecho(
          String titulo,
          String descripcion,
          String categoria,
          Double latitud,
          Double longitud,
          LocalDate fechaDelHecho
          //OrigenHecho origen
  ) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.latitud = latitud;
    this.longitud = longitud;
    this.fechaDelHecho = fechaDelHecho;
    this.fechaCreacion = LocalDate.now();
    //this.origen = origen;
    this.estado =  EstadoDelHecho.ACTIVO; //el hecho por defecto está activo
  }

  ////GETTERS///

  public String getTitulo() {
    return titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getCategoria() {
    return categoria;
  }
  /*
  public HechoLugarDto getLugar() {
    return lugar;
  }*/

  public Double getLatitud() {
    return latitud;
  }
  public Double getLongitud() {
    return longitud;
  }

  public LocalDate getFechaDelHecho() {
    return fechaDelHecho;
  }

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }
  /*
  public OrigenHecho getOrigen() {
    return origen;
  }*/

  public EstadoDelHecho getEstado() {
    return estado;
  }

  ////METODOS///

  public void desactivar() {
    this.estado = EstadoDelHecho.INACTIVO;
  }


}


