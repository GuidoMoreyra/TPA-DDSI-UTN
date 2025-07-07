package ar.edu.utn.frba.dds.models.modosnavegacion;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.contracts.ModoDeNavegacion;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;


public class Irrestricta implements ModoDeNavegacion {
  private final List<Fuente> fuentes;

  public Irrestricta(List<Fuente> fuentes) {
    this.fuentes = new ArrayList<>(fuentes); //copia defensiva
  }

  @Override
  public List<Hecho> obtenerHechos() {
    return new ArrayList<>(fuentes.stream()
        .flatMap(f -> f.obtenerHechos().stream())
        .toList());
  }
}
