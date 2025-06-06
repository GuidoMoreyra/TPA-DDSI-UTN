package ar.edu.utn.frba.dds.dto;

import com.opencsv.bean.CsvBindByName;
import java.time.LocalDate;

public class HechoCsvDto {

  @CsvBindByName
  public String titulo;

  @CsvBindByName
  public String descripcion;

  @CsvBindByName
  public String categoria;

  @CsvBindByName
  public double latitud;

  @CsvBindByName
  public double longitud;

  @CsvBindByName
  public LocalDate fechaDelHecho;

  @CsvBindByName
  public LocalDate horaHecho;

  // Nuevos campos agregados
  public String contenidoMultimedia = "";
  public Integer idSolicitudAgregacion = null;
}
