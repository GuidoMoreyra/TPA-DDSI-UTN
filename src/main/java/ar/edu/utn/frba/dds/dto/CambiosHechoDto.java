package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Coordenada;
import lombok.Data;


@Data
public class CambiosHechoDto {

  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Coordenada coordenadas;
  private OrigenHecho origen;

  public String getTitulo() {
    return titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getCategoria() {
    return categoria;
  }

  public String getContenidoMultimedia() {
    return contenidoMultimedia;
  }

  public Coordenada getCoordenadas() {
    return coordenadas;
  }

  public OrigenHecho getOrigen() {
    return origen;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public void setContenidoMultimedia(String contenidoMultimedia) {
    this.contenidoMultimedia = contenidoMultimedia;
  }

  public void setCoordenadas(Coordenada coordenadas) {
    this.coordenadas = coordenadas;
  }

  public void setOrigen(OrigenHecho origen) {
    this.origen = origen;
  }
}
