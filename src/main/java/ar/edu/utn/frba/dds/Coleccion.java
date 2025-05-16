package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Coleccion {
  private String titulo;
  private List<Criterio> criterios = new ArrayList<>();
  //private List<Hecho> hechos = new ArrayList<>();
  private Fuente fuente;

  ////CONSTRUCTOR///

  //de esta manera me permite que se cree sin pasarle como parametro
  //la lista de criterios y me crea una vacia
  // y que despues se agreguen
  public Coleccion(String titulo, Fuente fuente, List<Criterio> criterios) {
    this.titulo = titulo;
    this.criterios = (criterios != null ? criterios : new ArrayList<>());
    this.fuente = fuente;
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

  /*public void cargarHechos(List<Hecho> hechosDisponibles) {
    List<Hecho> hechosFiltrados = hechosDisponibles.stream()
        .filter(this::cumpleTodosLosCriterios)
        .toList();

    this.hechos.addAll(hechosFiltrados);}*/

  public void mostrarColeccion() {
    for (Hecho hecho : fuente.obtenerHechos()) {
      if (cumpleTodosLosCriterios(hecho)) {
        imprimirColeccion(hecho);
      }
    }
  }

  private boolean cumpleTodosLosCriterios(Hecho hecho) {
    return criterios.stream().allMatch(unCriterio -> unCriterio.cumple(hecho));
  }

  private void imprimirColeccion(Hecho hecho) {

    System.out.println();
  }

  public String getTitulo() {
    return titulo;
  }
}





//TODO - se tienen que filtrar los hechos por estado y quedarse con los estado = activo
