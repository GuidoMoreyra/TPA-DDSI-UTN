package ar.edu.utn.frba.dds.repositories.fuentes;

import ar.edu.utn.frba.dds.exceptions.FuenteProxyException;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;

public class FuenteIntermedia implements Fuente {


  private Fuente fuenteQueSeUsa;

  public void configurarFuenteIntermedia(Fuente fuenteQueSeUsa) {
    validacionFuente(fuenteQueSeUsa);
    this.fuenteQueSeUsa = fuenteQueSeUsa;
  }

  public void validacionFuente(Fuente unaFuente) {
    if (unaFuente == null) {
      throw new FuenteProxyException("configuracion incorrecta de la fuente que se usa");
    }
  }

  public List<Hecho> obtenerHechos() {
    return fuenteQueSeUsa.obtenerHechos();

  }

}
