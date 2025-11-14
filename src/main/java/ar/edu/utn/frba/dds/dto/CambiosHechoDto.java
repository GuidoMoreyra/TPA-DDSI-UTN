package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Coordenada;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;

@Data
@SuppressFBWarnings("EI_EXPOSE_REP")
public class CambiosHechoDto {

  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Coordenada coordenadas;
  private OrigenHecho origen;
}
