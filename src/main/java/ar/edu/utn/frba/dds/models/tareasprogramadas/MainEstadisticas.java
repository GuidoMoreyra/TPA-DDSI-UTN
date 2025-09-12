package ar.edu.utn.frba.dds.models.tareasprogramadas;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.TipoDeConsenso;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteEstatica;
import java.time.LocalDate;

public class MainEstadisticas {
  public static void main(String[] args) {

    Fuente fuenteestatica = new FuenteEstatica("formatoTp");

    Fuente fuenteestatica2 = new FuenteEstatica("hechos");

    Coleccion coleccion = new Coleccion(fuenteestatica,
        "Buenos Aires",
        LocalDate.of(1979, 1, 1),
        LocalDate.now(),
        "Incendio Forestal",
        TipoDeConsenso.MAYORIA_SIMPLE

    );

    Coleccion coleccionDos = new Coleccion(
        fuenteestatica2,
        "Buenos Aires",
        LocalDate.of(1979, 2, 3),
        LocalDate.now(),
        "Siniestro",
        TipoDeConsenso.MAYORIA_SIMPLE
    );


    System.out.println(coleccion.getCategoria());
    System.out.println(coleccionDos.getCategoria());

  }
}
