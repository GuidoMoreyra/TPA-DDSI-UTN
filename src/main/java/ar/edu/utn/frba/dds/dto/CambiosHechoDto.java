package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Coordenada;
import lombok.Data;

@Data
public final class CambiosHechoDto {

  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Coordenada coordenadas;
  private OrigenHecho origen;

}
