package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.EjecutarConsenso;
import ar.edu.utn.frba.dds.models.algoritmos.ConsensoAbsoluto;
import ar.edu.utn.frba.dds.models.algoritmos.MayoriaSimple;
import ar.edu.utn.frba.dds.models.algoritmos.MultiplesMenciones;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteDinamica;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteEstatica;
import java.util.ArrayList;
import java.util.List;

public final class MainAplicarConsensos {
  public static void main(String[] args) {

    Fuente fuentedinamica = new FuenteDinamica();

    Fuente fuenteestatica = new FuenteEstatica("formatoTp");

    Fuente fuenteestatica2 = new FuenteEstatica("hechos");

    List<Fuente> fuentesactivas = new ArrayList<>();
    fuentesactivas.add(fuentedinamica);
    fuentesactivas.add(fuenteestatica);
    fuentesactivas.add(fuenteestatica2);

    var algoritmos =
        List.of(
            new ConsensoAbsoluto(fuentesactivas),
            new MayoriaSimple(fuentesactivas),
            new MultiplesMenciones());

    EjecutarConsenso consensuar = new EjecutarConsenso();
    consensuar.aplicarConsensovdos(fuentesactivas, algoritmos);
  }
}
