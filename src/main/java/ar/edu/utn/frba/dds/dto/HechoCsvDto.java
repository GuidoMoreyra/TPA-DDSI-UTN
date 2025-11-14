package ar.edu.utn.frba.dds.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Data;

@Data
public final class HechoCsvDto {

  @CsvBindByName(column = "titulo")
  private String titulo;

  @CsvBindByName(column = "descripcion")
  private String descripcion;

  @CsvBindByName(column = "categoria")
  private String categoria;

  @CsvBindByName(column = "latitud")
  private double latitud;

  @CsvBindByName(column = "longitud")
  private double longitud;

  @CsvDate("dd/MM/yyyy")
  @CsvBindByName(column = "fecha_Del_Hecho")
  private LocalDate fechaDelHecho;

  private String contenidoMultimedia = "";

  @CsvDate("HH:mm:ss")
  @CsvBindByName(column = "Hora_Hecho")
  private LocalTime horaHecho;

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

  public LocalTime getHoraHecho() {
    return horaHecho;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }
}
