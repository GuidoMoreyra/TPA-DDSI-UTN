package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Entity
@DiscriminatorValue("Agregacion")
public final class FuenteDeAgregacion extends Fuente {

  @Getter
  @Setter
  @Transient
  private  List<Fuente> fuentes;

  public FuenteDeAgregacion(List<Fuente> fuentes) {
    this.fuentes = new ArrayList<>(fuentes);
  }

  public FuenteDeAgregacion() {

  }

  public List<Fuente> getFuentes() {
    return new ArrayList<>(fuentes);
  }

  public void setFuentes(List<Fuente> fuentes) {
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
