package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;


public final class Coleccion {

  private final List<Criterio> criteriosDeCreacion = new ArrayList<>();
  @Getter
  private final Fuente fuente;

  ///  La coleccion siempre se carga con los 3 criterios de pertenencia
  ///  (titulo , fecha , localidad) que sirven para cargar los hechos desde la fuente.

  public Coleccion(
      Fuente fuente,
      String localidad,
      LocalDate fechaInicial,
      LocalDate fechaFinal,
      String categoria
  ) {

    this.fuente = fuente;

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
    return fuente
        .obtenerHechos()
        .stream()
        .filter((Hecho h) ->
            this.cumpleCriterios(h, criteriosDeCreacion) && this.cumpleCriterios(h, criterios)
        ).toList();
  }

}