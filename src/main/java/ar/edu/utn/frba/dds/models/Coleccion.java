package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
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

  private final List<Criterio> criteriosDeCreacion = new ArrayList<>();
  @Getter
  private  Fuente fuente;
  private List<Hecho> hechosConsensuados;
  private TipoDeConsenso algoritmoDeconsenso;
  /// private  AlgoritmoDeConsenso algoritmoDeConseso; //comento porque se considera bug

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

    this.fuente = fuente;
    this.algoritmoDeconsenso = algoritmo;
    //this.algoritmoDeConseso = algoritmo; //se comenta porque por el momento es bug

    /// TODO - Habria que verificar que fecha 1 sea anterior a fecha 2
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
    ///  La fuente deberia devolver solo hechos activos.
    return fuente
        .obtenerHechos()
        .stream()
        .filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
        .toList();
  }

  public List<Hecho> obtenerColeccionConCriteriosAdicionales(List<Criterio> criterios) {
    ///  La fuente deberia devolver solo hechos activos.
    return this.obtenerColeccion()
        .stream()
        .filter((Hecho h) ->
             this.cumpleCriterios(h, criterios)
        ).toList();
  }

  private List<Hecho> obtenerHechosConsensuados() {

    if (algoritmoDeconsenso == null) {
      hechosConsensuados.addAll(this.obtenerColeccion());
      //si no tengo un algoritmo me trae los hehcos de su fuente primitiva
    }
    hechosConsensuados = HechosRepository.getInstance()
        .hechosFiltradosPorConsenso(algoritmoDeconsenso)
        .stream().filter(
            hecho -> this.cumpleCriterios(hecho, criteriosDeCreacion)
        ).collect(Collectors.toList());
    // si tengo algoritmo usa el metodo filtro dentro del repo
    // me filtra los hechos que tengan el algoritmo dentro de su lista
    //luego los filtra por criterios de creacion



    return hechosConsensuados;
  }



}