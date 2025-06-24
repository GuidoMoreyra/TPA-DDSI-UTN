package ar.edu.utn.frba.dds.dto;

import com.opencsv.bean.CsvBindByName;
import java.time.LocalDate;

public class HechoCsvDto {

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


}
