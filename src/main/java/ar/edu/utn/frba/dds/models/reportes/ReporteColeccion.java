package ar.edu.utn.frba.dds.models.reportes;

import ar.edu.utn.frba.dds.contracts.Reporte;
import ar.edu.utn.frba.dds.dto.ColeccionDto;

public class ReporteColeccion implements Reporte {

  private ColeccionDto dto;

  public ReporteColeccion(ColeccionDto dto) {
    this.dto = new ColeccionDto(dto);
  }

  @Override
  public String generarCsv() {
    String encabezado = "CategoriaBuscada,CategoriaConMasHechos,"
        + "ProvinciaConMasHechos,horaPicoDeHechos,ProvinciaSegunCategoria";
    String fila = dto.getCategoria() + "," + dto.getCategoriaConMasHechos()
        + "," + dto.getProvinciaConMasHechos() + ","
        + dto.getHoraPicoHechos()
        + "," + dto.getProvincia() + "\n";
    return encabezado + "\n" + fila;
  }

}