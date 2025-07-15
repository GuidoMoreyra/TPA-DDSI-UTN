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
import lombok.Getter;


public final class Coleccion {

  @Getter
  private  Fuente fuente;
  private TipoDeConsenso algoritmoDeconsenso;
  private final List<Criterio> criteriosDeCreacion = new ArrayList<>();
  private final HechosRepository repositorio = HechosRepository.getInstance() ;

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

  public Boolean cumpleCriterios(Hecho hecho, List<Criterio> criterios) {
    return criterios
        .stream()
        .allMatch(criterio -> criterio.cumple(hecho));
  }

  public List<Hecho> obtenerColeccion() {
    return fuente
        .obtenerHechos()
        .stream()
        .filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
        .filter((Hecho h) -> repositorio.verificaConsenso(h, algoritmoDeconsenso))
        .toList();
  }

  public List<Hecho> obtenerColeccionConCriteriosAdicionales(List<Criterio> criterios) {
    return this.obtenerColeccion()
        .stream()
        .filter((Hecho h) ->
             this.cumpleCriterios(h, criterios)
        ).toList();
  }
  /*
  public void actualizarHechosConsensuados() {

    if (algoritmoDeconsenso == null) {
      hechosConsensuados.addAll(this.obtenerColeccion());
      //si no tengo un algoritmo me trae los hehcos de su fuente primitiva
    }
    hechosConsensuados = HechosRepository.getInstance()
        .hechosFiltradosPorConsenso(algoritmoDeconsenso)
        .stream().filter(
            hecho ->
                hecho.estaActivo()
                    && this.cumpleCriterios(hecho, criteriosDeCreacion)
        ).collect(Collectors.toList());
    // si tengo algoritmo usa el metodo filtro dentro del repo
    // me filtra los hechos que tengan el algoritmo dentro de su lista
    //luego los filtra por criterios de creacion

  }

  public List<Hecho> obtenerHechosConsensuados() {
    return new ArrayList<>(hechosConsensuados);
  }

  public List<Hecho> aplicarCriteriosAdicionales(List<Criterio> criterios) {

    List<Hecho> aux = List.of();
    if (algoritmoDeconsenso == null) {
      aux = obtenerColeccion()
          .stream().filter(
              hecho -> this.cumpleCriterios(hecho, criterios)
          ).toList();
    }
    aux = this.obtenerHechosConsensuados()
        .stream().filter(
            hecho -> this.cumpleCriterios(hecho, criterios))
        .toList();


    return new ArrayList<>(aux);
  }

   */

  private void validar(LocalDate fechaInicial, LocalDate fechaFinal) {

    if (fechaInicial.isAfter(fechaFinal)) {
      throw new FechaException("fecha inicial no puede ser posterior a fecha final");
    }
  }


}