package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.CriterioCategoria;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.repositories.fuentes.Fuente;
import ar.edu.utn.frba.dds.models.criterios.Criterio;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Coleccion {
  private static int globalCount;
  private final int id;
  public final String categoria;
  private List<Criterio> criteriosDeCreacion = new ArrayList<>();
  private List<Criterio> criteriosDeUsuario = new ArrayList<>();
  private final List<Hecho> hechos;
  private final Fuente fuente;

  ////CONSTRUCTOR///


  ///  La coleccion siempre se carga con los 3 criterios de pertenencia (titulo , fecha , localidad) que
  ///   sirven para cargar los hechos desde el archivo.
  ///
  /// Los criterios del Usuario se cargaran previamente mediante
  ///   otras llamadas a la coleccion.

  public Coleccion(Fuente fuente, String localidad,
                   LocalDate fechaInicial,LocalDate fechaFinal,
                   String categoria) {
    id = globalCount++;
    this.categoria = categoria;

    /// Se asume que la fuente es valida.
    this.fuente = fuente;

    criteriosDeUsuario.add(new CriterioLugar(localidad));

    /// Habria que verificar que fecha 1 sea anterior a fecha 2
    criteriosDeUsuario.add(new CriterioFecha(fechaInicial, fechaFinal));

    criteriosDeUsuario.add(new CriterioCategoria(categoria));
    hechos = fuente
              .ObtenerHechos()
              .stream()
              .filter(hecho -> hecho.getEstado() == true)
              .toList();
  }

  ////METODOS///

  public void agregarCriterio(Criterio criterio) {
    if (criteriosDeUsuario.contains(criterio)) {

    }

  }

  public void quitarCriterio(Criterio criterio) {
    criteriosDeUsuario.remove(criterio);
  }




  /// Getters

  public String getCategoria() {
    return categoria;
  }

  public List<Hecho> getHechos() {
    return new ArrayList<>(hechos);
  }

  public int getId() {
    return id;
  }
}






