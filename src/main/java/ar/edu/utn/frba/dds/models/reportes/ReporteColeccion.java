package ar.edu.utn.frba.dds.models.reportes;

import ar.edu.utn.frba.dds.contracts.Reporte;
import ar.edu.utn.frba.dds.enums.Provincia;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReporteColeccion extends Reporte {

  private String categoriaConMasHechos;
  private Provincia provinciaConMasHechos;
  private Integer horaDePicoSegunCategoria;
  private Provincia provinciaSegunCategoria;

  public ReporteColeccion(String categoria, Provincia provincia,
                          Integer horaDePicoSegunCategoria,
                          Provincia provinciaSegunCategoria
  ) {
    this.categoriaConMasHechos = categoria;
    this.provinciaConMasHechos = provincia;
    this.horaDePicoSegunCategoria = horaDePicoSegunCategoria;
    this.provinciaSegunCategoria = provinciaSegunCategoria;
  }

  @Override
  public String generarCsv() {
    String encabezado = "CategoriaConMasHechos,"
        + "ProvinciaConMasHechos,horaPicoDeHechos,ProvinciaSegunCategoria";
    String fila = this.getCategoriaConMasHechos() + ","
        + "," + this.getProvinciaConMasHechos() + ","
        + this.getHoraDePicoSegunCategoria()
        + "," + this.getProvinciaSegunCategoria() + "\n";
    return encabezado + "\n" + fila;
  }

}