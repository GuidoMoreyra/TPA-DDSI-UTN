package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.dto.CambiosHechoDto;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;



@SuppressFBWarnings("EI_EXPOSE_REP")

@Getter
@Entity
@Table(name = "hechos")
public class Hecho {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  private String titulo;

  private String descripcion;

  private String categoria;

  @Column(name = "contenido_multimedia")
  private String contenidoMultimedia;

  @Embedded
  private Coordenada coordenadas;

  @Column(name = "fecha_hecho")
  private LocalDate fechaDelHecho;

  @Column(name = "fecha_creacion", updatable = false)
  private final LocalDate fechaCreacion = LocalDate.now();

  @Setter
  @Enumerated(EnumType.STRING)
  @Column(name = "origen")
  private OrigenHecho origen;

  @Setter
  @Getter(AccessLevel.NONE)
  @ElementCollection
  @CollectionTable(joinColumns = @JoinColumn(name = "hecho_id"))
  @Column(name = "algoritmo")
  private List<TipoDeConsenso> algoritmos = new ArrayList<>();

  /*atributo agregado para estadisticas*/
  @Enumerated(EnumType.STRING)
  private Provincia provincia = null;

  private LocalTime horaHecho;


  public Hecho() {}

  public Hecho(
      String titulo,
      String descripcion,
      String categoria,
      double latitud,
      double longitud,
      LocalDate fechaDelHecho,
      OrigenHecho origen,
      String contenidoMultimedia,
      LocalTime horaDelHecho
  ) {
    this.contenidoMultimedia = contenidoMultimedia;
    this.titulo = titulo;
    this.descripcion = descripcion;
    this.categoria = categoria;
    this.coordenadas = new Coordenada(latitud, longitud);
    this.fechaDelHecho = fechaDelHecho;
    this.origen = origen;
    this.provincia = this.establecerProvincia();
    this.horaHecho = horaDelHecho;
  }


  public Coordenada getLugar() {
    return new Coordenada(coordenadas.latitud, coordenadas.longitud);
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

  /*metodo para estadisticas*/
  public Provincia establecerProvincia() {

    return this.coordenadas.obtenerProvincia();
  }

  public LocalTime horaDelHecho() {
    return this.horaHecho;
  }


}