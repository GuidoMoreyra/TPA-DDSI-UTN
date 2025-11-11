package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.exceptions.FuenteProxyException;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue("Intermedia")
public final class FuenteIntermedia extends Fuente {

  @Transient
  private  AdaptadorFuenteDemo fuenteQueSeUsa;

  @Transient
  private List<Hecho> hechosObtenidos;

  public void configurarFuenteIntermedia(AdaptadorFuenteDemo fuenteQueSeUsa) {
    validacionFuente(fuenteQueSeUsa);
    this.fuenteQueSeUsa = fuenteQueSeUsa;
  }

  public void validacionFuente(AdaptadorFuenteDemo unaFuente) {
    if (unaFuente == null) {
      throw new FuenteProxyException("configuracion incorrecta de la fuente que se usa");
    }
  }

  public void actualizarHechos() {
    hechosObtenidos.addAll(this.fuenteQueSeUsa.obtenerHechos());
  }

  @Override
  public List<Hecho> obtenerHechos()  {

    return new ArrayList<>(hechosObtenidos);

  }


  public void actualizar() {
    fuenteQueSeUsa.actualizar();
  }

}
