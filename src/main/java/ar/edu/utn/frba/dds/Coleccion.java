package ar.edu.utn.frba.dds;

import static java.util.stream.Collectors.toList;

import ar.edu.utn.frba.dds.hecho.models.Hecho;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Coleccion {
  private String titulo;
  private List<Criterio> criterios = new ArrayList<>();
  private List<Hecho> hechos = new ArrayList<>();
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

  public void cargarHechos() {
    for(Hecho unHecho : fuente.obtenerHechos()){
      if(cumpleTodosLosCriterios(unHecho)){
        hechos.add(unHecho);
      }
    }
  }

  public void cargarHechosv2() {
    List<Hecho> filtrados = StreamSupport.stream(fuente.obtenerHechos().spliterator(), false)
        .filter(this::cumpleTodosLosCriterios)
        .toList();
    hechos.addAll(filtrados);
  }

  //por el momento son criterios pero se pueden crear filtros especificos para los usuarios
  public List<Hecho> cargarHechosConFiltros( List<Criterio> nuevosCriterios) {
    return hechos.stream().filter(unhecho ->
        nuevosCriterios.stream().allMatch(criterio->
            criterio.cumple(unhecho)))
            .collect(Collectors.toList());
  }

  /*
  public void mostrarColeccion() {
    hechos.forEach(hecho -> {imprimirColeccion(hecho);});
  }*/

  private boolean cumpleTodosLosCriterios(Hecho hecho) {
    return criterios.stream().allMatch(unCriterio -> unCriterio.cumple(hecho));
  }
  /*
  private void imprimirColeccion(Hecho hecho) {

    System.out.println();
  }*/

  public String getTitulo() {
    return titulo;
  }

  public List<Hecho> getHechos() {
    return hechos;
  }
}





//TODO - se tienen que filtrar los hechos por estado y quedarse con los estado = activo
