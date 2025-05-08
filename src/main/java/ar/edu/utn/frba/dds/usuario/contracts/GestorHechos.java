package ar.edu.utn.frba.dds.usuario.contracts;

import ar.edu.utn.frba.dds.Coleccion;
import ar.edu.utn.frba.dds.SolicitudEliminacion;
import ar.edu.utn.frba.dds.hecho.contracts.GeneradorHecho;
import ar.edu.utn.frba.dds.hecho.enums.OrigenHecho;
import ar.edu.utn.frba.dds.hecho.generators.DTO.DatosFormularioDTO;
import ar.edu.utn.frba.dds.hecho.models.Hecho;
import ar.edu.utn.frba.dds.hecho.models.factories.GeneradorHechoFactory;

public interface GestorHechos {
    default Hecho subirHecho(DatosFormularioDTO datosFormulario) {
        GeneradorHecho generadorHecho = GeneradorHechoFactory.crear(
          OrigenHecho.DINAMICA,
          datosFormulario
        );

        return generadorHecho.generarHecho();
    };

    default SolicitudEliminacion solicitarEliminarUnHecho(Hecho hecho, String justificacion) {
        SolicitudEliminacion solicitud = new SolicitudEliminacion(
          hecho,
          this,
          justificacion
        );

        // TODO: agregar la solicitud a la lista de solicitudes

        return solicitud;
    }
    void mostrarColeccion(Coleccion coleccion);
}
