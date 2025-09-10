package ar.edu.utn.frba.dds.dto;

import ar.edu.utn.frba.dds.enums.Provincia;

public class ColeccionDto {

  private String categoriaConMasHechos;
  private Provincia provinciaConMasHechos;
  private Integer horaPicoHechos;
  private String categoria;
  private Provincia provincia;

  public String getCategoriaConMasHechos() {
    return categoriaConMasHechos;
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

  public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  public void setCategoriaConMasHechos(String categoriaConMasHechos) {
    this.categoriaConMasHechos = categoriaConMasHechos;
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

  public ColeccionDto(ColeccionDto dto) {
    this.categoriaConMasHechos = dto.getCategoriaConMasHechos();
    this.categoria = dto.getCategoria();
    this.horaPicoHechos = dto.getHoraPicoHechos();
    this.provincia = dto.getProvincia();
  }

  public ColeccionDto() {}
}
