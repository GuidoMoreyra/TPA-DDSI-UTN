package ar.edu.utn.frba.dds.models.reportes;

import ar.edu.utn.frba.dds.contracts.Reporte;

public class reporteColeccion implements Reporte {


  @Override
  public String generarCSV(){

  }
}
/*
* lo que pense es usar un coleccionDto
* una interfaz reporte e implementar reportecoleccion
* lo que pense es usar el dto coleccion y luego pasarlo al reporteColeccion para generar el dto a transformar el csv
*
* esto seria coleccionDto
* public class ColeccionDto {

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
} y quiero obtener a traves de
*
* */