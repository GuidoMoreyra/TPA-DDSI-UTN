package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.exceptions.FechaException;
import ar.edu.utn.frba.dds.models.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Getter;


@Entity
@Table(name = "colecciones")
public final class Coleccion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @Getter
  @ManyToOne
  @JoinColumn(name = "fuentes_id")
  private Fuente fuente;

  @ManyToOne
  @JoinColumn(name = "algoritmo_consenso_id")
  private AlgoritmoDeConsenso algoritmoDeconsenso;

 @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
    name = "colecciones_criterions",
    joinColumns = @JoinColumn(name = "coleccion_id"),
    inverseJoinColumns = @JoinColumn(name = "criterio_id")
  )
  private final List<Criterio> criteriosDeCreacion = new ArrayList<>();

  @Transient
  private final HechosRepository repositorio = HechosRepository.getInstance();
  //esto deberia irse porque lops hehcos se obtiene de la lista o cache de
  // la fuente para la comparacion de curado o no

  /*
  * Desnormalizando:guardar estadísticas precalculadas en cada colección.
  * */
  @Getter
  @Column(name = "hechos_reportados")
  private Integer cantidadHechosReportados = 0;
  @Getter
  @Enumerated(EnumType.STRING)
  @Column(name = "provincia_con_mas_hechos")
  private Provincia provinciaConMasHechos = Provincia.PROVINCIA_DESCONOCIDA;
  @Getter
  @Column(name = "hora_pico")
  private Integer horaPicoHechos = 0;
  @Getter
  private String categoria;

  @Getter
  @ManyToMany (cascade = CascadeType.PERSIST)
  @JoinTable(
      name = "colecciones_hechos",
      joinColumns = @JoinColumn(name = "coleccion_id"),
      inverseJoinColumns = @JoinColumn(name = "hecho_id")
  )
  private List<Hecho> hechos = new ArrayList<>();
  //esto deberia ir en la fuente salvo fuente de cargada por el usuario

  public Coleccion(
      Fuente fuente,
      String localidad,
      LocalDate fechaInicial,
      LocalDate fechaFinal,
      String categoria,
      AlgoritmoDeConsenso algoritmo
  ) {

    this.validar(fechaInicial, fechaFinal);
    this.fuente = fuente;
    this.algoritmoDeconsenso = algoritmo;

    criteriosDeCreacion.add(new CriterioFecha(fechaInicial, fechaFinal));

    criteriosDeCreacion.add(new CriterioLugar(localidad));

    criteriosDeCreacion.add(new CriterioCategoria(categoria));
    this.categoria = categoria;

  }

  public Coleccion() {}

  ////METODOS///
  public void agregarHechos() {
    this.hechos = new ArrayList<>(this.obtenerColeccionCriteriosCreacional(false));
  }

  private void validar(LocalDate fechaInicial, LocalDate fechaFinal) {
    if (fechaInicial.isAfter(fechaFinal)) {
      throw new FechaException("fecha inicial no puede ser posterior a fecha final");
    }
  }


  public Boolean cumpleCriterios(Hecho hecho, List<Criterio> criterios) {
    return criterios
        .stream()
        .allMatch(criterio -> criterio.cumple(hecho));
  }



  //cambiar nombre a dameHechosConCrietirosBasicos
  public List<Hecho> obtenerColeccionCriteriosCreacional(Boolean soloConsensuados) {
    //creo la variable auxiliar de hechos y en primera instancia son de la fuente
    Stream<Hecho> hechos = fuente.obtenerHechos().stream();

    //si me pide hechos consensuados me fijo en cada hecho de la fuente si
    //contiene el tipo de consenso a buscar y los traigo
    if (soloConsensuados) {
      hechos = hechos.filter(Hecho::tieneConsenso);
    }

    //si la lista que obtengo por ser consensuado o por la fuente esta vacia
    //la devuelvo vacia ya que uso .lolist()

    //caso contrario tengo hechos y le aplico los criterios (y la puede devolver vacia
    // si no se aplican)
    return hechos
          .filter(hecho -> this.cumpleCriterios(hecho, criteriosDeCreacion)
              && hecho.estaActivo()).toList();
  }


  //cambiar nombre por dameHechosConCriteriosUsuario
  public List<Hecho> obtenerColeccionConCriteriosExtra(
      List<Criterio> criteriosExtras, Boolean soloConsensuados) {

    List<Hecho> base = this.obtenerColeccionCriteriosCreacional(soloConsensuados);

    //si no hay hechos base, devuelvo una lista vacia.
    if (base.isEmpty()) {
      return List.of();
    }

    //si no hay criterios extra, devuelvo la lista base
    if (criteriosExtras.isEmpty()) {
      return base;
    }

    //si hay criterios extra, los aplico, para ambos casos
    //esto significa que se realizo la tarea programada y tenia criterios extra
    return base.stream()
            .filter(hecho -> this.cumpleCriterios(hecho, criteriosExtras))
            .toList();

  }

  // se agregan setter y getter manuales porque mvn clear verify dice que es un spotbug

  public List<Hecho> getHechos() {
    return new ArrayList<>(hechos);
  }

  // metodos para calcular los atributos de reporte


  public void calcularProvinciaConMasHechos() {

    List<Hecho> hechos = this.obtenerColeccionCriteriosCreacional(false);
    // provincia, cantidadDeveces que aparece
    Map<Provincia, Integer> contador = new HashMap<>();
    Provincia maxProvincia = null;
    Integer maxCount = 0;

    for (Hecho hecho : hechos) {
      Provincia provincia = hecho.getProvincia();
      contador.put(provincia, contador.getOrDefault(provincia, 0) + 1);
    }

    for (Map.Entry<Provincia, Integer> entry : contador.entrySet()) {
      if (entry.getValue() > maxCount) {
        maxProvincia = entry.getKey();
        maxCount = entry.getValue();
      }
    }
    this.provinciaConMasHechos = maxProvincia;

  }




}