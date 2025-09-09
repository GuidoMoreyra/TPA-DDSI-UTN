package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.enums.Provincia;

public class ColeccionDto {

  private Integer cantidadHechosReportados;
  private Provincia provinciaConMasHechos;
  private Integer horaPicoHechos;
  private String categoria;

  public Integer getCantidadHechosReportados() {
    return cantidadHechosReportados;
  }

  public Provincia getProvinciaConMasHechos() {
    return provinciaConMasHechos;
  }

  public Integer getHoraPicoHechos() {
    return horaPicoHechos;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCantidadHechosReportados(Integer cantidadHechosReportados) {
    this.cantidadHechosReportados = cantidadHechosReportados;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public void setHoraPicoHechos(Integer horaPicoHechos) {
    this.horaPicoHechos = horaPicoHechos;
  }

  public void setProvinciaConMasHechos(Provincia provinciaConMasHechos) {
    this.provinciaConMasHechos = provinciaConMasHechos;
  }
}
