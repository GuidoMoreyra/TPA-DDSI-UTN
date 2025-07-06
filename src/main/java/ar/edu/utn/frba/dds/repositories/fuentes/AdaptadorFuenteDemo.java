package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.contracts.Conexion;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.exceptions.InvalidoUrlExeception;
import ar.edu.utn.frba.dds.exceptions.UltimaConsultaException;
import ar.edu.utn.frba.dds.models.Hecho;
import java.net.MalformedURLException;
import java.net.URL;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class AdaptadorFuenteDemo {

  private final Conexion conexion;
  private final URL url;
  private LocalDateTime ultimaConsulta;
  //private final Integer intervaloDeEspera;
  private final List<Hecho> hechosObtenidos = new ArrayList<>();

  public AdaptadorFuenteDemo(
      Conexion conexion,
      String url,
      LocalDateTime ultimaConsulta
  //intervaloDeespera
  ) {
    validarUltimaConsulta(ultimaConsulta);
    this.conexion = conexion;
    this.url = validarUrl(url);
    this.ultimaConsulta = ultimaConsulta;
  }

  private URL validarUrl(String url) {
    try {
      return new URL(url);
    } catch (MalformedURLException e) {
      throw new InvalidoUrlExeception("URL  es invalido", e);
    }
  }

  private void validarUltimaConsulta(LocalDateTime ultimaConsulta) {
    if (ultimaConsulta == null) {
      throw new UltimaConsultaException("ultimaConsulta no puede ser null");
    }
  }


  public List<Hecho> obtenerHechos() {

    return new ArrayList<>(hechosObtenidos);
  }

  /*
  private boolean esMomentoDeObtenerHechos() {
    Duration duracion = Duration.between(ultimaConsulta, LocalDateTime.now());
    return duracion.toMinutes() >= intervaloDeEspera;
  }


   */

  /*
  private boolean deboActualizar() {
    return ultimaConsulta == null
        || ultimaConsulta.plus(intervaloDeEspera)
            .isBefore(LocalDateTime.now());
  }*/

  private Hecho construirHechoDesde(Map<String, Object> datos) {
    //Primera opcion parciando los datos
    String titulo = (String) datos.get("titulo");
    String descripcion = (String) datos.get("descripcion");
    String categoria = (String) datos.get("categoria");
    String contenidoMultimedia = (String) datos.get("contenidoMultimedia");
    Double latitud = (Double) datos.get("latitud");
    Double longitud = (Double) datos.get("longitud");
    OrigenHecho origen = OrigenHecho.INTERMEDIO;
    LocalDate fechaOcurrido = (LocalDate) datos.get("fecha");

    return new Hecho(
        titulo,
        descripcion,
        categoria,
        latitud,
        longitud,
        fechaOcurrido,
        origen,
        contenidoMultimedia
    );
  }

  public void actualizar() {

    Map<String, Object> datos;

    while ((datos = conexion.siguienteHecho(url, ultimaConsulta)) != null) {
      Hecho hecho = construirHechoDesde(datos);
      hechosObtenidos.add(hecho);
    }

    this.ultimaConsulta = LocalDateTime.now();

  }
}
