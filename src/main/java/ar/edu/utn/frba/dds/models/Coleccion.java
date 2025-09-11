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
import lombok.Getter;

import javax.persistence.*;

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

  }

  public Coleccion() {}

  ////METODOS///

  public Boolean cumpleCriterios(Hecho hecho, List<Criterio> criterios) {
    return criterios
        .stream()
        .allMatch(criterio -> criterio.cumple(hecho));
  }

  // TODO: Deprecar luego, el método de abajo (obtenerHechos) lo reemplaza porque va contra la DB
  public List<Hecho> obtenerColeccion() {
    return fuente
        .obtenerHechos()
        .stream()
        .filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
        .toList();
  }

  public List<Hecho> obtenerHechos() {
    List<Hecho> hechos = HechosRepository.getInstance().getHechos();

    return hechos
        .stream()
        .filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
        .toList();
  }

  public List<Hecho> aplicarConsenso() {
    return this.obtenerColeccion().stream()
        .filter((Hecho unHecho) -> repositorio.verificaConsenso(
            unHecho, algoritmoDeconsenso
        )).toList();
  }

  public List<Hecho> obtenerColeccionConCriteriosAdicionales(List<Criterio> criterios) {
    return this.obtenerColeccion()
        .stream()
        .filter((Hecho h) ->
             this.cumpleCriterios(h, criterios)
        ).toList();
  }

  public List<Hecho> obtenerColeccionConCriteriosExtra(List<Criterio> criteriosExtra) {
    return this.aplicarConsenso()
        .stream()
        .filter(hecho -> this.cumpleCriterios(hecho, criteriosExtra))
        .toList();
  }

  private void validar(LocalDate fechaInicial, LocalDate fechaFinal) {
    if (fechaInicial.isAfter(fechaFinal)) {
      throw new FechaException("fecha inicial no puede ser posterior a fecha final");
    }
  }
}