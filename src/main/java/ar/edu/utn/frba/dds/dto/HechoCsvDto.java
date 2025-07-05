package ar.edu.utn.frba.dds.dto;

import com.opencsv.bean.CsvBindByName;
import java.time.LocalDate;
import lombok.Data;

@Data
public final class HechoCsvDto {

  @CsvBindByName(column = "titulo")
  public String titulo;

  @CsvBindByName(column = "descripcion")
  public String descripcion;

  @CsvBindByName(column = "categoria")
  public String categoria;

  @CsvBindByName(column = "latitud")
  public double latitud;

  @CsvBindByName(column = "longitud")
  public double longitud;

  @CsvBindByName(column = "fecha_Del_Hecho")
  public LocalDate fechaDelHecho;

  public String contenidoMultimedia = "";

  public String getTitulo() {
    return titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getCategoria() {
    return categoria;
  }

  public double getLatitud() {
    return latitud;
  }

  public double getLongitud() {
    return longitud;
  }

  public LocalDate getFechaDelHecho() {
    return fechaDelHecho;
  }

  public String getContenidoMultimedia() {
    return contenidoMultimedia;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }
}
