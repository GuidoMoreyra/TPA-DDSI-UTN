package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Hecho {
  private String titulo;
  private String descripcion;
  private String categoria;
  private String contenidoMultimedia;
  private Coordenada coordenadas;
  private LocalDate fechaDelHecho;
  private LocalDate fechaCreacion = LocalDate.now();
  @Setter
  private OrigenHecho origen;
  @Getter(AccessLevel.NONE)
  private List<TipoDeConsenso> algoritmos = new ArrayList<>();

  public Hecho() {}


  public Hecho(
      String titulo,
      String descripcion,
      String categoria,
      double latitud,
      double longitud,
      LocalDate fechaDelHecho,
      OrigenHecho origen,
      String contenidoMultimedia
  ) {
    this.contenidoMultimedia = contenidoMultimedia;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.coordenadas = new Coordenada(longitud, latitud); // TODO: cambiar luego
    this.fechaDelHecho = fechaDelHecho;
    this.origen = origen;
  }


  ////GETTERS///

  public String getTitulo() {
    return titulo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getCategoria() {
    return categoria;
  }

  public Coordenada getCoordenadas() {
    return coordenadas;
  }

  public Coordenada getLugar() {
    return new Coordenada(coordenadas.longitud, coordenadas.latitud);
  }

  public String getLocalidad() {
    return this.coordenadas.getLocalidad();
  }

  public LocalDate getFechaDelHecho() {

    return fechaDelHecho;
  }

  public LocalDate getFechaCreacion() {
    return fechaCreacion;
  }

  public OrigenHecho getOrigen() {
    return origen;
  }


  public Boolean estaActivo() {
    return SolicitudesEliminacionRepository
        .getInstance()
        .obtenerSolicitudesConEstado(EstadoSolicitudEliminacion.APROBADO)
        .stream()
        .noneMatch(solicitud -> solicitud.esParaElHecho(this));
  }

  public void aplicarCambios(CambiosHechoDto cambios) {
    if (cambios.getTitulo() != null) {
      this.titulo = cambios.getTitulo();
    }
    if (cambios.getDescripcion() != null) {
      this.descripcion = cambios.getDescripcion();
    }
    if (cambios.getCategoria() != null) {
      this.categoria = cambios.getCategoria();
    }
    if (cambios.getContenidoMultimedia() != null) {
      this.contenidoMultimedia = cambios.getContenidoMultimedia();
    }
    //    if (cambios.getCoordenadas() != null) {
    //      this.coordenadas = cambios.getCoordenadas();
    //    }
    if (cambios.getOrigen() != null) {
      this.origen = cambios.getOrigen();
    }
  }

  public Boolean tieneSugerencias() {
    return SolicitudesAgregacionRepository
        .getInstance()
        .obtenerSolicitudesConEstado(EstadoSolicitudAgregacion.ACEPTADO_CON_SUGERENCIAS)
        .stream()
        .anyMatch(s -> s.getHecho().equals(this));
  }

  public boolean compararRigurosa(Hecho hechoCompar) {
    return this.getTitulo().equals(hechoCompar.getTitulo())
        && this.getDescripcion().equals(hechoCompar.getDescripcion())
        && this.getCategoria().equals(hechoCompar.getCategoria())
        && this.getCoordenadas().equals(hechoCompar.getCoordenadas())
        && this.getFechaDelHecho() == hechoCompar.getFechaDelHecho();
  }



  public boolean compararHecho(Hecho h) {
    return this.getTitulo().equals(h.getTitulo());
  }

  public void agregarConsenso(TipoDeConsenso algoritmo) {
    this.algoritmos.add(algoritmo);
  }

  public List<TipoDeConsenso> getConsensos() {
    return new ArrayList<>(algoritmos);
  }
}