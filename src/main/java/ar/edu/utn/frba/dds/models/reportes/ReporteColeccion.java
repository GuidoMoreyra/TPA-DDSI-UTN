package ar.edu.utn.frba.dds.models.reportes;

import ar.edu.utn.frba.dds.contracts.Reporte;
import ar.edu.utn.frba.dds.enums.Provincia;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reporte_coleccion")
public class ReporteColeccion extends Reporte {

  @Column(name = "categoria_con_mas_hechos")
  private String categoriaConMasHechos;
  @Column(name = "provincia_con_mas_hechos")
  private Provincia provinciaConMasHechos;
  @Column(name = "hora_pico_segun_categoria")
  private Integer horaDePicoSegunCategoria;
  @Column(name = "provincia_segun_categoria")
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