package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Reporte;
import ar.edu.utn.frba.dds.dto.ColeccionDto;
import ar.edu.utn.frba.dds.dto.SolicitudSpamDto;
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
  private Provincia provinciaConMasHechos;
  private String categoriaConMasHechos;
  private Provincia provinciaSegunCategoria;
  private Integer horaDePicoSegunCategoria;
  private Long cantidadSolicSpam;


  public ComponenteDeEstadisticas(ColeccionRepository repositorioColeccion,
                                  SolicitudesEliminacionRepository repoSolicitudesEliminacion,
                                  String categoria) {
    this.repoColeccion = repositorioColeccion;
    this.repoSolicitudesEliminacion = repoSolicitudesEliminacion;
    this.categoriaBuscar = categoria;
    this.cantidadSolicSpam = null;
    this.provinciaSegunCategoria = Provincia.PROVINCIA_DESCONOCIDA;
    this.provinciaConMasHechos = null;
    this.categoriaConMasHechos = null;
    this.horaDePicoSegunCategoria = 0;

  }

  public void actualizar() {

    repoColeccion.listar().forEach(coleccion -> {
      coleccion.cantidadHechosReportados();
      coleccion.calcularHoraPico();
      coleccion.setProvinciaConMasHechos(); });

    List<SolicitudEliminacion> solicitudEliminacions = repoSolicitudesEliminacion
        .getSolicitudes();

    this.cantidadSolicSpam = repoSolicitudesEliminacion
        .cantidadDeSolicitudesSpam(solicitudEliminacions);
    this.provinciaConMasHechos = this.buscarProvinciaConMasHechos();
    this.categoriaConMasHechos = this.buscarCategoriaConMasHechos();
    this.provinciaSegunCategoria = this.buscarProvinciaConMasHechosDeCategoria(categoriaBuscar);
    this.horaDePicoSegunCategoria = this.buscarHoraPicoDeCategoria(categoriaBuscar);

  }

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

  public Provincia buscarProvinciaConMasHechosDeCategoria(String categoria) {
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

  public int buscarHoraPicoDeCategoria(String categoria) {
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

  public ColeccionDto generarColeccionDto() {
    ColeccionDto dto = new ColeccionDto();
    dto.setCategoria(this.categoriaBuscar);
    dto.setCategoriaConMasHechos(this.getCategoriaConMasHechos());
    dto.setProvinciaConMasHechos(this.getProvinciaConMasHechos());
    dto.setHoraPicoHechos(this.getHoraDePicoSegunCategoria());
    dto.setProvincia(this.getProvinciaSegunCategoria());

    return dto;
  }

  public String generarReporteColeccion() {
    ColeccionDto dto = this.generarColeccionDto();
    Reporte reporteColeccion = new ReporteColeccion(dto);
    String csv = reporteColeccion.generarCsv();
    return csv;
  }

  public SolicitudSpamDto generarSolicitudSpamDto() {
    SolicitudSpamDto dto = new SolicitudSpamDto();
    dto.setCantidadDeSolicitudSpam(this.getCantidadSolicSpam());
    return dto;
  }

  public String generarSolicitudSpam() {
    SolicitudSpamDto dto = this.generarSolicitudSpamDto();
    Reporte reporteSolicitud = new ReporteSolicitudElim(dto);
    return reporteSolicitud.generarCsv();
  }

  public void exportarReporte(String rutaReporte, Reporte reporte) {
    ExportadorCsv exportadorCsv = new ExportadorCsv();
    exportadorCsv.exportarCsvArchivo(rutaReporte, reporte);
  }



}


