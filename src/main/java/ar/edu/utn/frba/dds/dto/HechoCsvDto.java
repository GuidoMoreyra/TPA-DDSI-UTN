package ar.edu.utn.frba.dds.dto;

import com.opencsv.bean.CsvBindByName;
import java.time.LocalDate;
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

  @CsvBindByName(column = "fecha_Del_Hecho")
  private LocalDate fechaDelHecho;

  private String contenidoMultimedia = "";

}
