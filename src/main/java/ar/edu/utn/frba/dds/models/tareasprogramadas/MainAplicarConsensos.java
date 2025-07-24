package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.EjecutarConsenso;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteDinamica;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteEstatica;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    EjecutarConsenso consensuar = new EjecutarConsenso(fuentesactivas);
    consensuar.evaluarVersionDos();






  }
}

