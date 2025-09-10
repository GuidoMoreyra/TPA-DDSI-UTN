package ar.edu.utn.frba.dds.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

  @CsvDate("dd/MM/yyyy")
  @CsvBindByName(column = "fecha_Del_Hecho")
  public LocalDate fechaDelHecho;

  public String contenidoMultimedia = "";

  @CsvDate("HH:mm:ss")
  @CsvBindByName(column = "Hora_Hecho")
  public LocalTime horaHecho;

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
