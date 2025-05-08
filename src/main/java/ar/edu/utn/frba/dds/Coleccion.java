package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;

import java.util.ArrayList;
import java.util.List;

public class Coleccion {
  private String titulo;
  private List<Criterio> criterios = new ArrayList<>();
  private List<Hecho> hechos = new ArrayList<>();

  ////CONSTRUCTOR///

  public Coleccion(String titulo, List<Criterio> criterios) {
    this.titulo = titulo;
    this.criterios = criterios;
    //no se incluyen los hechos porque la coleccion se puede crear vacia
  }

  ////METODOS///

  public void agregarCriterio(Criterio criterio) {
    criterios.add(criterio);
  }

  public void quitarCriterio(Criterio criterio) {
    criterios.remove(criterio);
  }

  public boolean tieneCriterio(Criterio criterio) {
    return criterios.contains(criterio);
  }

  public void cargarHechos(List<Hecho> hechosDisponibles) {
    List<Hecho> hechosFiltrados = hechosDisponibles.stream()
        .filter(this::cumpleTodosLosCriterios)
        .toList();

    this.hechos.addAll(hechosFiltrados);
  }

  private boolean cumpleTodosLosCriterios(Hecho hecho) {
    return criterios.stream().allMatch(c -> c.cumple(hecho));
  }
}

//TODO - se tienen que filtrar los hechos por estado y quedarse con los estado = activo
