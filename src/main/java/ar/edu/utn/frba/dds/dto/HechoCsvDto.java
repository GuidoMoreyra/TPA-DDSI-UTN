package ar.edu.utn.frba.dds.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
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

  @CsvDate("dd/MM/yyyy")
  @CsvBindByName(column = "fecha_Del_Hecho")
  public LocalDate fechaDelHecho;

  public String contenidoMultimedia = "";

}
