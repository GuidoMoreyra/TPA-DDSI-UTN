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
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Getter;


@Entity
public final class Coleccion {

  @Getter
  @Id
  @GeneratedValue
  private Long id;


  @Transient
  private  Fuente fuente;

  @Enumerated(EnumType.STRING)
  private TipoDeConsenso algoritmoDeconsenso;


  /*
  * Esto de aca seria un OneToMany
  * Criterio seria una tabla de herencia
  *porque los criterios son record y tienen parametros en el nombre de la clase
  *
  *
  * */
  @Transient
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
  private Provincia provinciaConMasHechos = Provincia.PROVINCIA_DESCONOCIDA;
  @Getter
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

    this.categoria = categoria;


  }

  public Coleccion() {}//por algun motivo me lo pide despues de hacer @Entity

  ////METODOS///

  public Boolean cumpleCriterios(Hecho hecho, List<Criterio> criterios) {
    return criterios
        .stream()
        .allMatch(criterio -> criterio.cumple(hecho));
  }

  public List<Hecho> obtenerColeccion() {
    return fuente
        .obtenerHechos()
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

  /*
  * metodos para calcular los atributos de reporte
  * */

  public void cantidadHechosReportados() {
    cantidadHechosReportados = this.obtenerColeccion().size();
  }

  public void setProvinciaConMasHechos() {

    List<Hecho> hechos = this.obtenerColeccion();
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

  /*
  * dentro de la coleccion
  * tengo que hacer esto: ¿A qué hora del día ocurren la mayor
  *  cantidad de hechos de una cierta categoría?
  * tengo el atributo  horaPicoHechos, como cada coleccion tiene su
  * categoria lo unico que faltaria es buscar
  * por horario ir iterando hecho por hecho y fijarse cual es la hora donde mas hechos ocurrieron
  *Por el momento solo me da la hora de 0 a 23
  *
  *
  * */

  public void calcularHoraPico() {

    /*
    * Map<hora,cantidadDehechos>
    * */
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