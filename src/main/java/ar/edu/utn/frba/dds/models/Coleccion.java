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
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Getter;

@Entity
public final class Coleccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Getter
  @ManyToOne
  private  Fuente fuente;
  @Enumerated(EnumType.STRING)
  @Column(name = "consenso")
  private TipoDeConsenso algoritmoDeconsenso;
  @ManyToMany
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

  ////METODOS///


  public List<Hecho> aplicarConsenso(List<Criterio> criteriosExtras) {

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
}