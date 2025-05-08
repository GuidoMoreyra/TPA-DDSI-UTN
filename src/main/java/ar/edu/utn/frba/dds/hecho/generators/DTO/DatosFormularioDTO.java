package ar.edu.utn.frba.dds.hecho.generators.DTO;

import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.models.DatosGeneradorHecho;

import java.time.LocalDate;

public class DatosFormularioDTO extends DatosGeneradorHecho {
  public String titulo;
  public String descripcion;
  public String categoria;
  public String contenidoMultimedia;
  public String lugar;
  public LocalDate fechaAcontecimiento;
  public OrigenHecho origenHecho;

  public DatosFormularioDTO(
      String titulo,
      String descripcion,
      String categoria,
      String contenidoMultimedia,
      String lugar,
      LocalDate fechaAcontecimiento
  ) {
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.contenidoMultimedia = contenidoMultimedia;
    this.lugar = lugar;
    this.fechaAcontecimiento = fechaAcontecimiento;
    this.origenHecho = OrigenHecho.DINAMICA;
  }
}
