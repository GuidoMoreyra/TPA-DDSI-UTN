package ar.edu.utn.frba.dds.enums;

import ar.edu.utn.frba.dds.contracts.Solicitud;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;

public enum Operacion {
    ACEPTAR {
        @Override
        public void ejecutar(Solicitud solicitud) {
            solicitud.aceptarSolicitud(); // tu método en la entidad
        }
    },
    RECHAZAR {
        @Override
        public void ejecutar(Solicitud solicitud) {
            solicitud.rechazarSolicitud(); // tu método en la entidad
        }
    };

    public abstract void ejecutar(Solicitud solicitud);
}