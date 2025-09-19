package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Reporte;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.reportes.ReporteColeccion;
import ar.edu.utn.frba.dds.models.reportes.ReporteSolicitudElim;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import ar.edu.utn.frba.dds.utils.ExportadorCsv;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;

@Getter
public class ComponenteDeEstadisticas {

  private String categoriaBuscar;
  private ColeccionRepository repoColeccion;
  private SolicitudesEliminacionRepository repoSolicitudesEliminacion;
  private Provincia provinciaConMasHechos = null;
  private String categoriaConMasHechos = null;
  private Provincia provinciaConMasHechosPorCategoria = Provincia.PROVINCIA_DESCONOCIDA;
  private Integer horaDePicoSegunCategoria = null;
  private Long cantidadSolicSpam = null;


  public ComponenteDeEstadisticas(ColeccionRepository repositorioColeccion,
                                  SolicitudesEliminacionRepository repoSolicitudesEliminacion,
                                  String categoria) {
    this.repoColeccion = repositorioColeccion;
    this.repoSolicitudesEliminacion = repoSolicitudesEliminacion;
    this.categoriaBuscar = categoria;


  }

  public void actualizar() {

    repoColeccion.listar().forEach(coleccion -> {
      coleccion.calcularHechosReportados();
      coleccion.calcularHoraPico();
      coleccion.calcularProvinciaConMasHechos(); });

    List<SolicitudEliminacion> solicitudEliminacions = repoSolicitudesEliminacion
        .getSolicitudes();
    /*¿Cuántas solicitudes de eliminación son spam?*/
    this.cantidadSolicSpam = repoSolicitudesEliminacion
        .cantidadDeSolicitudesSpam(solicitudEliminacions);
    this.provinciaConMasHechos = this.buscarProvinciaConMasHechos();
    this.categoriaConMasHechos = this.buscarCategoriaConMasHechos();
    this.provinciaConMasHechosPorCategoria = this.buscarProvinciaConMasHechosPorCategoria(categoriaBuscar);
    this.horaDePicoSegunCategoria = this.buscarHoraPicoPorCategoria(categoriaBuscar);

  }

  /*¿Cuál es la categoría con mayor cantidad de hechos reportados?*/
  public String buscarCategoriaConMasHechos() {

    Map<String, Integer> contadorCategorias = new HashMap<>();
    // Buscar la categoría con máximo valor
    String categoriaMax = null;

    int maxCantidad = 0;

    for (Coleccion c : repoColeccion.listar()) {
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

  /*¿En qué provincia se presenta la mayor cantidad de hechos de una cierta categoría?*/
  public Provincia buscarProvinciaConMasHechosPorCategoria(String categoria) {
    List<Coleccion> colecciones = repoColeccion.listar()
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
  /*¿en qué provincia se agrupan la mayor cantidad de hechos reportados?*/
  public Provincia buscarProvinciaConMasHechos() {
    Map<Provincia, Integer> contadorProvincias = new HashMap<>();

    repoColeccion.listar().forEach(coleccion -> {
      Provincia provincia = coleccion.getProvinciaConMasHechos();
      contadorProvincias.put(provincia,
          contadorProvincias.getOrDefault(provincia, 0) + 1);
    });

    return contadorProvincias.entrySet().stream()
        .max(Map.Entry.comparingByValue())
        .map(Map.Entry::getKey)
        .orElse(Provincia.PROVINCIA_DESCONOCIDA);
  }

  /*¿A qué hora del día ocurren la mayor cantidad de hechos de una cierta categoría?*/
  public int buscarHoraPicoPorCategoria(String categoria) {
    Map<Integer, Integer> contadorHoras = new HashMap<>();
    List<Coleccion> colecciones = repoColeccion.listar()
        .stream().filter(coleccion -> coleccion.getCategoria().equals(categoria)).toList();

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


  public ReporteColeccion generarReporteColeccion() {
    ReporteColeccion reporteColeccion = new ReporteColeccion(
        categoriaConMasHechos,
        provinciaConMasHechos,
        horaDePicoSegunCategoria,
        provinciaConMasHechosPorCategoria
    );
    return  reporteColeccion;
  }



  public ReporteSolicitudElim generarReporteSolicitudSpam() {
    ReporteSolicitudElim reporteSolicitud = new ReporteSolicitudElim(
        this.getCantidadSolicSpam());
    return reporteSolicitud;
  }

  public void exportarReporte(String rutaReporte, Reporte reporte) {
    ExportadorCsv exportadorCsv = new ExportadorCsv();
    exportadorCsv.exportarCsvArchivo(rutaReporte, reporte);
  }



}


