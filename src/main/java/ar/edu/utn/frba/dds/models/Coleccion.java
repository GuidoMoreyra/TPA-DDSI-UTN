package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.exceptions.FechaException;
import ar.edu.utn.frba.dds.models.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
  private  Fuente fuente;

  @Enumerated(EnumType.STRING)
  @Column(name = "consenso")
  private TipoDeConsenso algoritmoDeconsenso;

  @ManyToMany
  @JoinTable(
      name = "colecciones_criterions",
      joinColumns = @JoinColumn(name = "coleccion_id"),
      inverseJoinColumns = @JoinColumn(name = "criterio_id")
  )
  private final List<Criterio> criteriosDeCreacion = new ArrayList<>();

  @Transient
  private final HechosRepository repositorio = HechosRepository.getInstance();

  ///  La coleccion siempre se carga con los 3 criterios de pertenencia
  ///  (titulo , fecha , localidad) que sirven para cargar los hechos desde la fuente.

  /*
  * Desnormalizando:guardar estadísticas precalculadas en cada colección.
  * */
  @Getter
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

  public Coleccion(
      Fuente fuente,
      String localidad,
      LocalDate fechaInicial,
      LocalDate fechaFinal,
      String categoria,
      TipoDeConsenso algoritmo
  ) {

    this.validar(fechaInicial, fechaFinal);
    this.fuente = fuente;
    this.algoritmoDeconsenso = algoritmo;

    criteriosDeCreacion.add(new CriterioFecha(fechaInicial, fechaFinal));

    criteriosDeCreacion.add(new CriterioLugar(localidad));

    criteriosDeCreacion.add(new CriterioCategoria(categoria));

  }

  public Coleccion() {}

  ////METODOS///

  public Boolean cumpleCriterios(Hecho hecho, List<Criterio> criterios) {
    return criterios
        .stream()
        .allMatch(criterio -> criterio.cumple(hecho));
  }

  // TODO: Deprecar luego, el método de abajo (obtenerHechos) lo reemplaza porque va contra la DB
  public List<Hecho> obtenerColeccion() {
    return fuente
        .obtenerHechos()
        .stream()
        .filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
        .toList();
  }

  public List<Hecho> obtenerHechos() {
    List<Hecho> hechos = HechosRepository.getInstance().getHechos();

    return hechos
        .stream()
        .filter((Hecho h) -> this.cumpleCriterios(h, criteriosDeCreacion))
        .toList();
  }

  public List<Hecho> aplicarConsenso() {
    return this.obtenerColeccion().stream()
        .filter((Hecho unHecho) -> repositorio.verificaConsenso(
            unHecho, algoritmoDeconsenso
        )).toList();
  }

  public List<Hecho> obtenerColeccionConCriteriosAdicionales(List<Criterio> criterios) {
    return this.obtenerColeccion()
        .stream()
        .filter((Hecho h) ->
             this.cumpleCriterios(h, criterios)
        ).toList();
  }

  public List<Hecho> obtenerColeccionConCriteriosExtra(List<Criterio> criteriosExtra) {
    return this.aplicarConsenso()
        .stream()
        .filter(hecho -> this.cumpleCriterios(hecho, criteriosExtra))
        .toList();
  }

  private void validar(LocalDate fechaInicial, LocalDate fechaFinal) {
    if (fechaInicial.isAfter(fechaFinal)) {
      throw new FechaException("fecha inicial no puede ser posterior a fecha final");
    }
  }


  // metodos para calcular los atributos de reporte

  public void cantidadHechosReportados() {
    cantidadHechosReportados = this.obtenerColeccion().size();
  }

  public void setProvinciaConMasHechos() {

    List<Hecho> hechos = this.obtenerColeccion();
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


  public void calcularHoraPico() {

    //Map<hora del dia (0-23),cantidadDehechos>

    Map<Integer, Integer> contadorHoras = new HashMap<>();
    List<Hecho> hechos = this.obtenerColeccion();
    hechos.forEach(hecho -> {
      Integer hora = hecho.horaDelHecho().getHour(); // obtenemos la hora del día (0-23)
      contadorHoras.put(hora, contadorHoras.getOrDefault(hora, 0) + 1);

    });


    // Encontrar la hora con mayor cantidad de hechos
    int maxHora = -1;
    int maxCount = -1;
    for (Map.Entry<Integer, Integer> entry : contadorHoras.entrySet()) {
      if (entry.getValue() > maxCount) {
        maxCount = entry.getValue();
        maxHora = entry.getKey();
      }
    }

    this.horaPicoHechos = maxHora; // guardamos la hora pico en la colección
  }


}