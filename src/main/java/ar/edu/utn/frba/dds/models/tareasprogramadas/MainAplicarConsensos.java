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
import java.util.ArrayList;
import java.util.List;

public final class MainAplicarConsensos {
  public static void main(String[] args) {
    //1 crear cada fuente a utilizar a usar listo
    //2 crear la lista de fuentes listo
    //3 crear el objeto EjecutarCosenso liato
    //4 crear los hechos a repetir
    //5 lista de hechos a repetir
    //6 ejecutar consensuar.evaluar(hechos)

    // 1. Crear hechos simulados
    Hecho hechoDinamico1 = new Hecho(
        "Incendio en la Patagonia",
        "Se desconoce las causas del incendio",
        "Incendio Forestal",
        -36, -38,
        LocalDate.of(2017, 5, 1),
        OrigenHecho.DINAMICO,
        "foto1.jpg"
    );

    Hecho hechoDinamico2 = new Hecho(
        "Desaparicion en buenos aires",
        "Se acusa a el gobierno de la desaparicion de estudiantes",
        "Desaparicion vinculada al gobierno",
        -34, -58,
        LocalDate.of(1978, 5, 29),
        OrigenHecho.DINAMICO,
        "foto2.jpg"
    );

    Hecho hechoDinamico3 = new Hecho(
        "Incendio en el Parque Nacional Nahuel Huapi",
        "Campistas no apagaron debidamente las brazas",
        "Incendio Forestal",
        -40, -71,
        LocalDate.of(2022, 9, 12),
        OrigenHecho.DINAMICO,
        "foto3.jpg"
    );

    // 2. Crear solicitudes
    SolicitudAgregacion solicitud1 = new SolicitudAgregacion(hechoDinamico1, false);
    solicitud1.aceptarSolicitud();

    SolicitudAgregacion solicitud2 = new SolicitudAgregacion(hechoDinamico2, false);
    solicitud2.aceptarSolicitud(); // o aceptarSolicitudConSugerencias(sugerencias)

    SolicitudAgregacion solicitud3 = new SolicitudAgregacion(hechoDinamico3, false);
    solicitud3.aceptarSolicitud();

    // 3. Agregar al repositorio
    SolicitudesAgregacionRepository repo = SolicitudesAgregacionRepository.getInstance();
    repo.agregarSolicitud(solicitud1);
    repo.agregarSolicitud(solicitud2);
    repo.agregarSolicitud(solicitud3);

    Fuente fuentedinamica = new FuenteDinamica();

    Fuente fuenteestatica = new FuenteEstatica("formatoTp.csv");

    Fuente fuenteestatica2 = new FuenteEstatica("hechos.csv");

    List<Fuente> fuentesactivas = new ArrayList<>();
    fuentesactivas.add(fuentedinamica);
    fuentesactivas.add(fuenteestatica);
    fuentesactivas.add(fuenteestatica2);

    EjecutarConsenso consensuar = new EjecutarConsenso(fuentesactivas);
    List<Hecho> hechos = new ArrayList<>(); //hechos repetidos

    hechos.add(hechoDinamico1);
    hechos.add(hechoDinamico2);

    consensuar.evaluar(hechos);

  }
}

