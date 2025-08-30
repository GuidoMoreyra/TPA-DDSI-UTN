package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@SuppressFBWarnings("EI_EXPOSE_REP")

@Getter
@Entity
public class Hecho {
  /**
   */
  @Id
  @GeneratedValue
  private Long id;

  private String titulo;

  private String descripcion;

  private String categoria;
  private String contenidoMultimedia;

  @OneToOne
  private Coordenada coordenadas;

  private LocalDate fechaDelHecho;

  private LocalDate fechaCreacion = LocalDate.now();

  @Setter
  private OrigenHecho origen;
  @Setter
  @Getter(AccessLevel.NONE)

  //@OneToMany
  @Transient
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
    this.coordenadas = new Coordenada(longitud, latitud);
    this.fechaDelHecho = fechaDelHecho;
    this.origen = origen;
  }


  public Coordenada getLugar() {
    return new Coordenada(coordenadas.longitud, coordenadas.latitud);
  }

  public String getLocalidad() {
    return this.coordenadas.getLocalidad();
  }


  public Boolean estaActivo() {
    return SolicitudesEliminacionRepository
        .getInstance()
        .obtenerSolicitudesConEstado(EstadoSolicitudEliminacion.APROBADO)

        .stream()
        .noneMatch(solicitud -> solicitud.esParaElHecho(this));
    //busca que no tenga solicutud de eliminacion aprobada
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

  public boolean comparacionRigurosa(Hecho hechoCompar) {
    return this.getTitulo().equals(hechoCompar.getTitulo())
        && this.getDescripcion().equals(hechoCompar.getDescripcion())
        && this.getCategoria().equals(hechoCompar.getCategoria())
        && this.getCoordenadas().equals(hechoCompar.getCoordenadas())
        && this.getFechaDelHecho().equals(hechoCompar.getFechaDelHecho());

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

  public void setLocalidad(String localidad) {
    this.coordenadas.setLocalidad(localidad);
  }


}