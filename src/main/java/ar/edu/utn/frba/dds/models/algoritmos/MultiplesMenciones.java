package ar.edu.utn.frba.dds.models.algoritmos;

import ar.edu.utn.frba.dds.contracts.AlgoritmoDeConsenso;
import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("multiples-menciones")
public class MultiplesMenciones extends AlgoritmoDeConsenso {

  @Override
  public Boolean estaConsensuado(Hecho hecho, List<Hecho> hechosRepositorio) {
    long repeticiones =
        hechosRepositorio.stream().filter(hechoRepo -> hechoRepo.compararHecho(hecho)).count();

    return repeticiones > 1;
  }

  @Override
  public Boolean realizarConsenso(Hecho hecho, List<Fuente> fuentesActivas) {
    long repeticiones =
        fuentesActivas.stream()
            .filter(
                fuente ->
                    fuente.obtenerHechos().stream()
                        .anyMatch(hechoDeunafuente -> hecho.compararHecho(hechoDeunafuente)))
            .count();
    return repeticiones > 1;
  }
}
