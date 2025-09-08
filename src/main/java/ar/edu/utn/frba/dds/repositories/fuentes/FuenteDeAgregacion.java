package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;


public final class FuenteDeAgregacion extends Fuente {
  private final List<Fuente> fuentes;

  public FuenteDeAgregacion(List<Fuente> fuentes) {
    this.fuentes = new ArrayList<>(fuentes);
  }
  
  @Override
  public List<Hecho> obtenerHechos() {
    List<Hecho> hechos = new ArrayList<>();

    for (Fuente fuente : fuentes) {
      List<Hecho> hechosFuente = fuente.obtenerHechos();

      hechosFuente.forEach(
          hecho -> hecho.setOrigen(
              OrigenHecho.mapearOrigenConAgregador(hecho.getOrigen())
          )
      );

      hechos.addAll(fuente.obtenerHechos());
    }

    return hechos;
  }
}
