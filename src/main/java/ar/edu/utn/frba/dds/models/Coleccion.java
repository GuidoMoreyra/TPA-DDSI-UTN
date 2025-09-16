package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.exceptions.FechaException;
import ar.edu.utn.frba.dds.models.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "colecciones")
public final class Coleccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @Getter
  @ManyToOne
  private  Fuente fuente;

  @Enumerated(EnumType.STRING)
  @Column(name = "consenso")
  private TipoDeConsenso algoritmoDeconsenso;

  @ManyToMany
  @JoinTable(
      name = "colecciones_criterions",
      joinColumns = @JoinColumn(name = "coleccion_id"),
      inverseJoinColumns = @JoinColumn(name = "criterio_id")
  )
  private final List<Criterio> criteriosDeCreacion = new ArrayList<>();

  @Transient
  private final HechosRepository repositorio = HechosRepository.getInstance();

  @Setter
  private boolean estaCurada;

  ///  La coleccion siempre se carga con los 3 criterios de pertenencia
  ///  (titulo , fecha , localidad) que sirven para cargar los hechos desde la fuente.

  public Coleccion(
      Fuente fuente,
      String localidad,
      LocalDate fechaInicial,
      LocalDate fechaFinal,
      String categoria,
      TipoDeConsenso algoritmo
  ) {

    this.validar(fechaInicial, fechaFinal);
    this.fuente = fuente;
    this.algoritmoDeconsenso = algoritmo;

    criteriosDeCreacion.add(new CriterioFecha(fechaInicial, fechaFinal));

    criteriosDeCreacion.add(new CriterioLugar(localidad));

    criteriosDeCreacion.add(new CriterioCategoria(categoria));
    this.estaCurada = false;

  }

  public Coleccion() {}

  ////METODOS///


  public List<Hecho> aplicarConsensoConCriteriosExtra(List<Criterio> criteriosExtras) {

    return this.obtenerColeccion(criteriosExtras).stream()
        .filter((Hecho unHecho) -> repositorio.verificaConsenso(
            unHecho, algoritmoDeconsenso
        )).toList();
  }

  public Boolean cumpleCriterios(Hecho hecho, List<Criterio> criterios) {
    return criterios
        .stream()
        .allMatch(criterio -> criterio.cumple(hecho));
  }

  public List<Hecho> obtenerColeccion(List<Criterio> criteriosExtras) {
    return fuente
        .obtenerHechos()
        .stream()
        .filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
        .filter(h -> criteriosExtras == null || this.cumpleCriterios(h, criteriosExtras))
        .toList();
  }

  private void validar(LocalDate fechaInicial, LocalDate fechaFinal) {
    if (fechaInicial.isAfter(fechaFinal)) {
      throw new FechaException("fecha inicial no puede ser posterior a fecha final");
    }
  }

  public List<Hecho> obtenerColeccionVersionDos() {
    if (estaCurada) {
      return fuente.obtenerHechos()
          .stream().filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
          .filter((Hecho unHecho) -> repositorio.verificaConsenso(
              unHecho, algoritmoDeconsenso
          )).toList();
    } else {
      return fuente.obtenerHechos()
          .stream().filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
          .toList();
    }
  }

  public List<Hecho> obtenerColeccionConCriteriosExtra(
      List<Criterio> criteriosExtras
  ) {
    if (estaCurada) {
      return this.aplicarConsensoConCriteriosExtra(criteriosExtras);
    } else {
      return fuente.obtenerHechos()
          .stream().filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
          .filter(h -> this.cumpleCriterios(h, criteriosExtras))
          .toList();
    }
  }

}