package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.models.criterios.Criterio;
import ar.edu.utn.frba.dds.models.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.repositories.fuentes.Fuente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Coleccion {
  private static int globalCount;
  public final String categoria;
  private List<Criterio> criteriosDeCreacion = new ArrayList<>();
  private List<Criterio> criteriosDeUsuario = new ArrayList<>();
  private final Fuente fuente;

  ////CONSTRUCTOR///


  ///  La coleccion siempre se carga con los 3 criterios de pertenencia
  ///  (titulo , fecha , localidad) que
  ///   sirven para cargar los hechos desde el archivo.
  ///
  /// Los criterios del Usuario se cargaran previamente mediante
  ///   otras llamadas a la coleccion.

  public Coleccion(Fuente fuente, String localidad,
                   LocalDate fechaInicial, LocalDate fechaFinal,
                   String categoria) {
    this.categoria = categoria;

    /// Se asume que la fuente es valida.
    this.fuente = fuente;

    criteriosDeCreacion.add(new CriterioLugar(localidad));

    /// Habria que verificar que fecha 1 sea anterior a fecha 2
    criteriosDeCreacion.add(new CriterioFecha(fechaInicial, fechaFinal));

    criteriosDeCreacion.add(new CriterioCategoria(categoria));
  }

  ////METODOS///

  private static synchronized int generarNuevoId() {
    return globalCount++;
  }


  public void agregarCriterio(Criterio criterio) {
    if (!criteriosDeUsuario.contains(criterio)) {
      criteriosDeUsuario.add(criterio);
    }
  }

  public void quitarCriterio(Criterio criterio) {
    criteriosDeUsuario.remove(criterio);
  }

  public String getCategoria() {
    return categoria;
  }

  public List<Hecho> getHechos() {
    return fuente
        .obtenerHechos()
        .stream()
        .filter(Hecho::estaActivo)
        .toList();
  }
}






