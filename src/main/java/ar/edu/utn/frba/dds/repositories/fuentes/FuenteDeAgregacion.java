package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import lombok.Setter;

@Entity
@DiscriminatorValue("Agregacion")
public class FuenteDeAgregacion extends Fuente {
  @Setter @Transient private List<Fuente> fuentes;

  @Transient private List<Hecho> hechos;

  public FuenteDeAgregacion(List<Fuente> fuentes) {

    this.fuentes = new ArrayList<>(fuentes);
    this.hechos = new ArrayList<>();
  }

  public FuenteDeAgregacion() {}

  @Override
  public List<Hecho> obtenerHechos() {
    List<Hecho> hechos = new ArrayList<>();

    for (Fuente fuente : fuentes) {
      List<Hecho> hechosFuente = fuente.obtenerHechos();

      hechos.addAll(hechosFuente);
    }
    return hechos;
  }

  public void actualizarHechos() {
    hechos.addAll(this.obtenerHechos());
  }

  /* se agregaron los getters y setters para pasar el mvn clean verify*/

  public List<Fuente> getFuentes() {
    return new ArrayList<>(fuentes);
  }

  public void setFuentes(List<Fuente> fuentes) {
    this.fuentes = new ArrayList<>(fuentes);
  }
}
