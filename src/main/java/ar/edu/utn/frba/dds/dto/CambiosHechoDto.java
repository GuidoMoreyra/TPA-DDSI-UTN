package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.enums.OrigenHecho;

public class CambiosHechoDto {

  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Coordenada coordenadas;
  private OrigenHecho origen;

  // Getters y setters

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public String getContenidoMultimedia() {
    return contenidoMultimedia;
  }

  public void setContenidoMultimedia(String contenidoMultimedia) {
    this.contenidoMultimedia = contenidoMultimedia;
  }

  public Coordenada getCoordenadas() {
    return coordenadas;
  }

  public void setCoordenadas(Coordenada coordenadas) {
    this.coordenadas = coordenadas;
  }

  public OrigenHecho getOrigen() {
    return origen;
  }

  public void setOrigen(OrigenHecho origen) {
    this.origen = origen;
  }
}
