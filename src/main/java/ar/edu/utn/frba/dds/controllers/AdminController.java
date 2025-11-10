package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminController implements WithSimplePersistenceUnit {

    /**
     * Verifica si el usuario tiene permisos de administrador
     */
    private boolean esAdmin(Context context) {
        if (context.sessionAttribute("user_id") == null) {
            return false;
        }

        Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
        return nivelAcceso != null && nivelAcceso >= 1;
    }

    /**
     * Agrega información de sesión al modelo
     */
    private void agregarInfoSesion(Context context, Map<String, Object> model) {
        if (context.sessionAttribute("user_id") != null) {
            model.put("user", true);
            model.put("nombre", context.sessionAttribute("nombre"));
            Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
            model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);
        }
    }

    /**
     * Lista todas las solicitudes de eliminación con filtros opcionales
     */
    public void listarSolicitudes(Context context) {
        // Verificar permisos de admin
        if (!esAdmin(context)) {
            context.redirect("/");
            return;
        }

        Map<String, Object> model = new HashMap<>();

        // Obtener filtro de estado (opcional)
        String estadoFiltro = context.queryParam("estado");

        // Obtener todas las solicitudes
        List<SolicitudEliminacion> todasLasSolicitudes = SolicitudesEliminacionRepository.getInstance()
                .getSolicitudes();

        // Contar solicitudes por estado (ANTES de aplicar filtros)
        long pendientes = todasLasSolicitudes.stream()
                .filter(s -> s.getEstado() == EstadoSolicitudEliminacion.PENDIENTE)
                .count();
        long aprobadas = todasLasSolicitudes.stream()
                .filter(s -> s.getEstado() == EstadoSolicitudEliminacion.APROBADO)
                .count();
        long rechazadas = todasLasSolicitudes.stream()
                .filter(s -> s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO
                        || s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO_AUTOMATICAMENTE)
                .count();

        // Aplicar filtro de estado si existe
        List<SolicitudEliminacion> solicitudesFiltradas = todasLasSolicitudes;
        if (estadoFiltro != null && !estadoFiltro.isEmpty() && !estadoFiltro.equals("todas")) {
            try {
                EstadoSolicitudEliminacion estado = EstadoSolicitudEliminacion.valueOf(estadoFiltro.toUpperCase());
                solicitudesFiltradas = todasLasSolicitudes.stream()
                        .filter(s -> s.getEstado() == estado)
                        .collect(Collectors.toList());
            } catch (IllegalArgumentException e) {
                // Si el estado no es válido, ignorar el filtro
            }
        }

        // Preparar datos para el template
        List<Map<String, Object>> solicitudesParaTemplate = solicitudesFiltradas.stream().map(s -> {
            Map<String, Object> solicitudMap = new HashMap<>();
            solicitudMap.put("id", s.getId());
            solicitudMap.put("hecho", s.getHecho());
            solicitudMap.put("estado", s.getEstado());
            solicitudMap.put("esSpam", s.isEsSpam());

            // Flags de estado para el template
            solicitudMap.put("isPendiente", s.getEstado() == EstadoSolicitudEliminacion.PENDIENTE);
            solicitudMap.put("isAprobado", s.getEstado() == EstadoSolicitudEliminacion.APROBADO);
            solicitudMap.put("isRechazado", s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO);
            solicitudMap.put("isRechazadoAuto", s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO_AUTOMATICAMENTE);

            // Truncar justificación para la tabla
            String justificacion = s.getJustificacion();
            if (justificacion != null && justificacion.length() > 100) {
                solicitudMap.put("justificacionTruncada", justificacion.substring(0, 100) + "...");
            } else {
                solicitudMap.put("justificacionTruncada", justificacion);
            }

            return solicitudMap;
        }).collect(Collectors.toList());

        model.put("solicitudes", solicitudesParaTemplate);
        model.put("totalSolicitudes", solicitudesFiltradas.size());
        model.put("pendientes", pendientes);
        model.put("aprobadas", aprobadas);
        model.put("rechazadas", rechazadas);

        // Flags de filtro para el template
        String filtroActual = estadoFiltro != null ? estadoFiltro : "todas";
        model.put("filtroTodas", filtroActual.equals("todas"));
        model.put("filtroPendiente", filtroActual.equals("pendiente"));
        model.put("filtroAprobado", filtroActual.equals("aprobado"));
        model.put("filtroRechazado", filtroActual.equals("rechazado"));

        // Para mensajes de feedback
        String mensaje = context.queryParam("mensaje");
        if (mensaje != null) {
            model.put("mensajeAprobada", mensaje.equals("aprobada"));
            model.put("mensajeRechazada", mensaje.equals("rechazada"));
        }
        model.put("error", context.queryParam("error") != null);

        agregarInfoSesion(context, model);
        context.render("admin-solicitudes.hbs", model);
    }

    /**
     * Muestra el detalle completo de una solicitud
     */
    public void verDetalleSolicitud(Context context) {
        // Verificar permisos de admin
        if (!esAdmin(context)) {
            context.redirect("/");
            return;
        }

        try {
            Long id = Long.parseLong(context.pathParam("id"));

            SolicitudEliminacion solicitud = SolicitudesEliminacionRepository.getInstance()
                    .getSolicitudes()
                    .stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (solicitud == null) {
                context.redirect("/admin/solicitudes");
                return;
            }

            Map<String, Object> model = new HashMap<>();
            model.put("solicitud", solicitud);
            model.put("hecho", solicitud.getHecho());

            // Flags de estado para el template
            model.put("isPendiente", solicitud.getEstado() == EstadoSolicitudEliminacion.PENDIENTE);
            model.put("isAprobado", solicitud.getEstado() == EstadoSolicitudEliminacion.APROBADO);
            model.put("isRechazado", solicitud.getEstado() == EstadoSolicitudEliminacion.RECHAZADO);
            model.put("isRechazadoAuto", solicitud.getEstado() == EstadoSolicitudEliminacion.RECHAZADO_AUTOMATICAMENTE);

            agregarInfoSesion(context, model);
            context.render("admin-solicitud-detalle.hbs", model);

        } catch (NumberFormatException e) {
            context.redirect("/admin/solicitudes");
        }
    }

    /**
     * Aprueba una solicitud de eliminación (marca el hecho como inactivo)
     */
    public void aprobarSolicitud(Context context) {
        // Verificar permisos de admin
        if (!esAdmin(context)) {
            context.redirect("/");
            return;
        }

        try {
            Long id = Long.parseLong(context.pathParam("id"));

            SolicitudEliminacion solicitud = SolicitudesEliminacionRepository.getInstance()
                    .getSolicitudes()
                    .stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (solicitud == null) {
                context.redirect("/admin/solicitudes");
                return;
            }

            // Cambiar estado de la solicitud
            // El hecho se marcará como inactivo automáticamente al tener una solicitud aprobada
            withTransaction(() -> {
                solicitud.modificarEstado(EstadoSolicitudEliminacion.APROBADO);
            });

            // Redirigir al listado con mensaje de éxito
            context.redirect("/admin/solicitudes?mensaje=aprobada");

        } catch (NumberFormatException e) {
            context.redirect("/admin/solicitudes");
        } catch (Exception e) {
            e.printStackTrace();
            context.redirect("/admin/solicitudes?error=true");
        }
    }

    /**
     * Rechaza una solicitud de eliminación
     */
    public void rechazarSolicitud(Context context) {
        // Verificar permisos de admin
        if (!esAdmin(context)) {
            context.redirect("/");
            return;
        }

        try {
            Long id = Long.parseLong(context.pathParam("id"));

            SolicitudEliminacion solicitud = SolicitudesEliminacionRepository.getInstance()
                    .getSolicitudes()
                    .stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst()
                    .orElse(null);

            if (solicitud == null) {
                context.redirect("/admin/solicitudes");
                return;
            }

            // Cambiar estado de la solicitud
            withTransaction(() -> {
                solicitud.modificarEstado(EstadoSolicitudEliminacion.RECHAZADO);
            });

            // Redirigir al listado con mensaje de éxito
            context.redirect("/admin/solicitudes?mensaje=rechazada");

        } catch (NumberFormatException e) {
            context.redirect("/admin/solicitudes");
        } catch (Exception e) {
            e.printStackTrace();
            context.redirect("/admin/solicitudes?error=true");
        }
    }
}
