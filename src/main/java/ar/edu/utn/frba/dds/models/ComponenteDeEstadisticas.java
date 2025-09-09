package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.repositories.ColeccionRepositorio;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComponenteDeEstadisticas {

  private String categoriaBuscar;
  private ColeccionRepositorio repoColeccion;
  private SolicitudesEliminacionRepository repoSolicitudesEliminacion;
  private Provincia provinciaConMasHechos;
  private String categoriaConMasHechos;
  private Provincia provinciaSegunCategoria;
  private Integer horaDePicoSegunCategoria;
  private Long cantidadSolicSpam;


  public ComponenteDeEstadisticas(ColeccionRepositorio repositorioColeccion
  , SolicitudesEliminacionRepository repoSolicitudesEliminacion, String categoria) {
    this.repoColeccion = repositorioColeccion;
    this.repoSolicitudesEliminacion = repoSolicitudesEliminacion;
    this.categoriaBuscar = categoria;
    this.cantidadSolicSpam = null;
    this.provinciaSegunCategoria = Provincia.PROVINCIA_DESCONOCIDA;
    this.provinciaConMasHechos = null;
    this.categoriaConMasHechos = null;
    this.horaDePicoSegunCategoria =0;

  }

  public void actualizar(){

    repoColeccion.getColecciones().forEach(coleccion -> {
      coleccion.cantidadHechosReportados();
      coleccion.calcularHoraPico();
      coleccion.setProvinciaConMasHechos();});

    List<SolicitudEliminacion> solicitudEliminacions = repoSolicitudesEliminacion.getSolicitudes();

    this.provinciaConMasHechos = this.provinciaConMasHechos();
    this.categoriaConMasHechos = this.categoriaConMasHechos();
    this.provinciaSegunCategoria = this.provinciaConMasHechosDeCategoria(categoriaBuscar);
    this.horaDePicoSegunCategoria = this.horaPicoDeCategoria(categoriaBuscar);
    this.cantidadSolicSpam = repoSolicitudesEliminacion.cantidadDeSolicitudesSpam(solicitudEliminacions);

  }

  public String categoriaConMasHechos() {
    List<Coleccion> colecciones = repoColeccion.getColecciones();
    Map<String, Integer> contadorCategorias = new HashMap<>();
    // Buscar la categoría con máximo valor
    String categoriaMax = null;
    int maxCantidad = 0;

    for (Coleccion c : colecciones) {
      String categoria = c.getCategoria();
      int cantidad = c.getCantidadHechosReportados();
      contadorCategorias.put(categoria,
          contadorCategorias.getOrDefault(categoria, 0) + cantidad);
    }


    for (Map.Entry<String, Integer> entry : contadorCategorias.entrySet()) {
      if (entry.getValue() > maxCantidad) {
        maxCantidad = entry.getValue();
        categoriaMax = entry.getKey();
      }
    }

    return categoriaMax;
  }

  public Provincia provinciaConMasHechosDeCategoria( String categoria) {
    List<Coleccion> colecciones = repoColeccion.getColecciones()
        .stream().filter(coleccion ->
            coleccion.getCategoria().equals(categoria)).toList();

    Map<Provincia, Integer> contadorProv = new HashMap<>();

    colecciones.forEach(coleccion -> {
      Provincia provincia = coleccion.getProvinciaConMasHechos();
      contadorProv.put(provincia,
          contadorProv.getOrDefault(provincia, 0) + 1);
    });



    return contadorProv.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(Provincia.PROVINCIA_DESCONOCIDA);
  }

  public Provincia provinciaConMasHechos() {
    List<Coleccion> colecciones = repoColeccion.getColecciones();
    Map<Provincia, Integer> contadorProvincias = new HashMap<>();

    colecciones.forEach(coleccion -> {
      Provincia provincia = coleccion.getProvinciaConMasHechos();
      contadorProvincias.put(provincia,
          contadorProvincias.getOrDefault(provincia, 0) + 1);
    });

    return contadorProvincias.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(Provincia.PROVINCIA_DESCONOCIDA);
  }

  public int horaPicoDeCategoria( String categoria) {
    Map<Integer, Integer> contadorHoras = new HashMap<>();
    List<Coleccion> colecciones = repoColeccion.getColecciones()
        .stream().filter(coleccion ->coleccion.getCategoria().equals(categoria)).toList();

    colecciones.forEach(coleccion -> {
      Integer hora = coleccion.getHoraPicoHechos();
      contadorHoras.put(hora,
          contadorHoras.getOrDefault(hora, 0) + 1);
    });

    return contadorHoras.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(-1); // -1 = sin resultados
  }



}
