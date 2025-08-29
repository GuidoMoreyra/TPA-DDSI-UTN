package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.exceptions.FuenteProxyException;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;

public final class FuenteIntermedia implements Fuente {

  private  AdaptadorFuenteDemo fuenteQueSeUsa;

  public void configurarFuenteIntermedia(AdaptadorFuenteDemo fuenteQueSeUsa) {
    validacionFuente(fuenteQueSeUsa);
    this.fuenteQueSeUsa = fuenteQueSeUsa;
  }

  public void validacionFuente(AdaptadorFuenteDemo unaFuente) {
    if (unaFuente == null) {
      throw new FuenteProxyException("configuracion incorrecta de la fuente que se usa");
    }
  }

  public List<Hecho> obtenerHechos() {

    return fuenteQueSeUsa.obtenerHechos();

  }


  public void actualizar() {
    fuenteQueSeUsa.actualizar();
  }

}
