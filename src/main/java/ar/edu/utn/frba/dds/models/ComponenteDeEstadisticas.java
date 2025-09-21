package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Reporte;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.reportes.ReporteColeccion;
import ar.edu.utn.frba.dds.models.reportes.ReporteSolicitudElim;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import ar.edu.utn.frba.dds.utils.ExportadorCsv;
import lombok.Getter;

@Getter
public class ComponenteDeEstadisticas {

  private Provincia provinciaConMasHechos;
  private Provincia provinciaConMasHehosSegunCategoria;
  private Integer horaPicoHechosSegunCategoria;
  private String categoriaConMasHechos;
  private Long cantidadSolicitudesSpam;

  private ColeccionRepository repoColeccion;
  private SolicitudesEliminacionRepository repoSolicitudesEliminacion;
  private HechosRepository repoHechosRepository;


  public ComponenteDeEstadisticas(ColeccionRepository repositorioColeccion,
                                  SolicitudesEliminacionRepository repoSolicitudesEliminacion,
                                  HechosRepository repoHechos, String categoria,
                                  Coleccion coleccion) {
    this.repoColeccion = repositorioColeccion;
    this.repoSolicitudesEliminacion = repoSolicitudesEliminacion;
    this.repoHechosRepository = repoHechos;
    this.actualizar(categoria,coleccion);

  }

  public void actualizar(String categoria, Coleccion coleccion) {
    this.provinciaConMasHechos = this.buscarProvinciaConMasHechosDeUnaColeccion(coleccion);
    this.provinciaConMasHehosSegunCategoria = this.buscarProvinciaConMasHechosPorCategoria(categoria);
    this.horaPicoHechosSegunCategoria = this.buscarHoraPicoPorCategoria(categoria);
    this.categoriaConMasHechos = this.buscarCategoriaConMasHechos();
    this.cantidadSolicitudesSpam = this.cantidadDeSolictudesEliminacionSpam();
  }

  /*¿Cuál es la categoría con mayor cantidad de hechos reportados?*/
  public String buscarCategoriaConMasHechos() {

    return repoHechosRepository.buscarCategoriaConMasHechos();
  }

  /*¿En qué provincia se presenta la mayor cantidad de hechos de una cierta categoría?*/
  public Provincia buscarProvinciaConMasHechosPorCategoria(String categoria) {
    return repoHechosRepository.buscarProvinciaConMasHechosPorCategoria(categoria);
  }

  /*¿en qué provincia se agrupan la mayor cantidad de hechos reportados?*/
  public Provincia buscarProvinciaConMasHechosDeUnaColeccion(Coleccion coleccion) {

    return repoColeccion.provinciaConMasHechos(coleccion.getId());
  }


  /*¿A qué hora del día ocurren la mayor cantidad de hechos de una cierta categoría?*/
  public Integer buscarHoraPicoPorCategoria(String categoria) {
    return repoHechosRepository.buscarHoraPicoDeHechosSegun(categoria);
  }

  public Long cantidadDeSolictudesEliminacionSpam(){
    return repoSolicitudesEliminacion.cantidadDeSolicitudesSpamDos();
  }


  public ReporteColeccion generarReporteColeccion() {
    ReporteColeccion reporteColeccion = new ReporteColeccion(
        this.categoriaConMasHechos,
        this.provinciaConMasHechos,
        this.horaPicoHechosSegunCategoria,
        this.provinciaConMasHehosSegunCategoria

    );
    return  reporteColeccion;
  }



  public ReporteSolicitudElim generarReporteSolicitudSpam() {
    ReporteSolicitudElim reporteSolicitud = new ReporteSolicitudElim(
        this.cantidadDeSolictudesEliminacionSpam());
    return reporteSolicitud;
  }

  public void exportarReporte(String rutaReporte, Reporte reporte) {
    ExportadorCsv exportadorCsv = new ExportadorCsv();
    exportadorCsv.exportarCsvArchivo(rutaReporte, reporte);
  }



}


