package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.models.enums.OrigenHecho;
import java.time.LocalDate;

public class Hecho {
  private static int contadorGlobal;
  private int id;
  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Coordenada cordenadas;
  private LocalDate fechaDelHecho;
  private LocalDate fechaCreacion;
  private OrigenHecho origen;
  private Boolean estaActivo;

  ////CONSTRUCTOR///

  public Hecho(String titulo, String descripcion, String categoria,
               double latitud, double longitud, LocalDate fechaDelHecho, OrigenHecho origen) {
    this(titulo, descripcion, categoria,  latitud,  longitud, fechaDelHecho, origen, null);
  }

  public Hecho(String titulo, String descripcion, String categoria,
               double latitud, double longitud, LocalDate fechaDelHecho, OrigenHecho origen,
               String contenidoMultimedia) {

    this.contenidoMultimedia = contenidoMultimedia;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    cordenadas = new Coordenada(longitud, latitud);
    this.fechaDelHecho = fechaDelHecho;
    this.fechaCreacion = LocalDate.now();
    this.origen = origen;
    this.estaActivo =  true; //El hecho por defecto está activo
    this.id = generarNuevoId();
  }

  private static synchronized int generarNuevoId() {
    return contadorGlobal++;
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

  public Coordenada getLugar() {
    return cordenadas;
  }

  public LocalDate getFechaDelHecho() {
    return fechaDelHecho;
  }

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }

  public OrigenHecho getOrigen() {
    return origen;
  }

  public Boolean getEstado() {
    return estaActivo;
  }

  public String getContenidoMultimedia() {
    return contenidoMultimedia;
  }

  public int getId() {
    return id;
  }


  ////METODOS///

  public void desactivar() {
    this.estaActivo = false;
  }


}


