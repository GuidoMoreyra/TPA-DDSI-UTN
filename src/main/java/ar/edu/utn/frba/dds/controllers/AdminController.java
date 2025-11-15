package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.ComponenteDeEstadisticas;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.repositories.ColeccionRepository;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AdminController implements WithSimplePersistenceUnit {

  /** Verifica si el usuario tiene permisos de administrador */
  private boolean esAdmin(Context context) {
    if (context.sessionAttribute("user_id") == null) {
      return false;
    }

    Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
    return nivelAcceso != null && nivelAcceso >= 1;
  }

  /** Agrega información de sesión al modelo */
  private void agregarInfoSesion(Context context, Map<String, Object> model) {
    if (context.sessionAttribute("user_id") != null) {
      model.put("user", true);
      model.put("nombre", context.sessionAttribute("nombre"));
      Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
      model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);
    }
  }

  /** Lista todas las solicitudes de eliminación con filtros opcionales */
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
    List<SolicitudEliminacion> todasLasSolicitudes =
        SolicitudesEliminacionRepository.getInstance().getSolicitudes();

    // Contar solicitudes por estado (ANTES de aplicar filtros)
    final long pendientes =
        todasLasSolicitudes.stream()
            .filter(s -> s.getEstado() == EstadoSolicitudEliminacion.PENDIENTE)
            .count();
    final long aprobadas =
        todasLasSolicitudes.stream()
            .filter(s -> s.getEstado() == EstadoSolicitudEliminacion.APROBADO)
            .count();
    final long rechazadas =
        todasLasSolicitudes.stream()
            .filter(
                s ->
                    s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO
                        || s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO_AUTOMATICAMENTE)
            .count();

    // Aplicar filtro de estado si existe
    List<SolicitudEliminacion> solicitudesFiltradas = todasLasSolicitudes;
    if (estadoFiltro != null && !estadoFiltro.isEmpty() && !estadoFiltro.equals("todas")) {
      try {
        EstadoSolicitudEliminacion estado =
            EstadoSolicitudEliminacion.valueOf(estadoFiltro.toUpperCase());
        solicitudesFiltradas =
            todasLasSolicitudes.stream()
                .filter(s -> s.getEstado() == estado)
                .collect(Collectors.toList());
      } catch (IllegalArgumentException e) {
        // Si el estado no es válido, ignorar el filtro
      }
    }

    // Preparar datos para el template
    List<Map<String, Object>> solicitudesParaTemplate =
        solicitudesFiltradas.stream()
            .map(
                s -> {
                  Map<String, Object> solicitudMap = new HashMap<>();
                  solicitudMap.put("id", s.getId());
                  solicitudMap.put("hecho", s.getHecho());
                  solicitudMap.put("estado", s.getEstado());
                  solicitudMap.put("esSpam", s.isEsSpam());

                  // Flags de estado para el template
                  solicitudMap.put(
                      "isPendiente", s.getEstado() == EstadoSolicitudEliminacion.PENDIENTE);
                  solicitudMap.put(
                      "isAprobado", s.getEstado() == EstadoSolicitudEliminacion.APROBADO);
                  solicitudMap.put(
                      "isRechazado", s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO);
                  solicitudMap.put(
                      "isRechazadoAuto",
                      s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO_AUTOMATICAMENTE);

                  // Truncar justificación para la tabla
                  String justificacion = s.getJustificacion();
                  if (justificacion != null && justificacion.length() > 100) {
                    solicitudMap.put(
                        "justificacionTruncada", justificacion.substring(0, 100) + "...");
                  } else {
                    solicitudMap.put("justificacionTruncada", justificacion);
                  }

                  return solicitudMap;
                })
            .collect(Collectors.toList());

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

  /** Muestra el detalle completo de una solicitud */
  public void verDetalleSolicitud(Context context) {
    // Verificar permisos de admin
    if (!esAdmin(context)) {
      context.redirect("/");
      return;
    }

    try {
      Long id = Long.parseLong(context.pathParam("id"));

      SolicitudEliminacion solicitud =
          SolicitudesEliminacionRepository.getInstance().getSolicitudes().stream()
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
      model.put(
          "isRechazadoAuto",
          solicitud.getEstado() == EstadoSolicitudEliminacion.RECHAZADO_AUTOMATICAMENTE);

      agregarInfoSesion(context, model);
      context.render("admin-solicitud-detalle.hbs", model);

    } catch (NumberFormatException e) {
      context.redirect("/admin/solicitudes");
    }
  }

  /** Aprueba una solicitud de eliminación (marca el hecho como inactivo) */
  public void aprobarSolicitud(Context context) {
    // Verificar permisos de admin
    if (!esAdmin(context)) {
      context.redirect("/");
      return;
    }

    try {
      Long id = Long.parseLong(context.pathParam("id"));

      SolicitudEliminacion solicitud =
          SolicitudesEliminacionRepository.getInstance().getSolicitudes().stream()
              .filter(s -> s.getId().equals(id))
              .findFirst()
              .orElse(null);

      if (solicitud == null) {
        context.redirect("/admin/solicitudes");
        return;
      }

      // Cambiar estado de la solicitud
      // El hecho se marcará como inactivo automáticamente al tener una solicitud aprobada
      withTransaction(
          () -> {
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

  /** Rechaza una solicitud de eliminación */
  public void rechazarSolicitud(Context context) {
    // Verificar permisos de admin
    if (!esAdmin(context)) {
      context.redirect("/");
      return;
    }

    try {
      Long id = Long.parseLong(context.pathParam("id"));

      SolicitudEliminacion solicitud =
          SolicitudesEliminacionRepository.getInstance().getSolicitudes().stream()
              .filter(s -> s.getId().equals(id))
              .findFirst()
              .orElse(null);

      if (solicitud == null) {
        context.redirect("/admin/solicitudes");
        return;
      }

      // Cambiar estado de la solicitud
      withTransaction(
          () -> {
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

  /** Muestra el panel de estadísticas */
  public void verEstadisticas(Context context) {
    // Verificar permisos de admin
    if (!esAdmin(context)) {
      context.redirect("/");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    try {
      // Crear componente de estadísticas
      ComponenteDeEstadisticas estadisticas =
          new ComponenteDeEstadisticas(
              new ColeccionRepository(),
              SolicitudesEliminacionRepository.getInstance(),
              HechosRepository.getInstance(),
              null, // No filtrar por categoría específica inicialmente
              null // No usar una colección específica
              );

      // Estadísticas generales (con manejo de nulls)
      String categoriaConMasHechos = estadisticas.getCategoriaConMasHechos();
      model.put(
          "categoriaConMasHechos", categoriaConMasHechos != null ? categoriaConMasHechos : "N/A");

      Provincia provinciaConMasHechos = estadisticas.getProvinciaConMasHechos();
      model.put(
          "provinciaConMasHechos",
          provinciaConMasHechos != null ? provinciaConMasHechos.toString() : "N/A");

      model.put("cantidadSolicitudesSpam", estadisticas.getCantidadSolicitudesSpam());

      // Estadísticas adicionales
      long totalHechos = HechosRepository.getInstance().getHechos().size();
      long hechosActivos =
          HechosRepository.getInstance().getHechos().stream().filter(Hecho::estaActivo).count();
      long totalSolicitudes =
          SolicitudesEliminacionRepository.getInstance().getSolicitudes().size();

      model.put("totalHechos", totalHechos);
      model.put("hechosActivos", hechosActivos);
      model.put("hechosInactivos", totalHechos - hechosActivos);
      model.put("totalSolicitudes", totalSolicitudes);

      // Distribución por categoría
      Map<String, Long> distribucionCategorias =
          HechosRepository.getInstance().getHechos().stream()
              .filter(Hecho::estaActivo)
              .collect(Collectors.groupingBy(Hecho::getCategoria, Collectors.counting()));

      // Convertir a lista para el template
      List<Map<String, Object>> categorias =
          distribucionCategorias.entrySet().stream()
              .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
              .limit(5)
              .map(
                  entry -> {
                    Map<String, Object> cat = new HashMap<>();
                    cat.put("nombre", entry.getKey());
                    cat.put("cantidad", entry.getValue());
                    // Evitar división por cero
                    long porcentaje =
                        hechosActivos > 0
                            ? Math.round((entry.getValue() * 100.0) / hechosActivos)
                            : 0;
                    cat.put("porcentaje", porcentaje);
                    return cat;
                  })
              .collect(Collectors.toList());

      model.put("distribucionCategorias", categorias);

      // Distribución por provincia
      Map<Provincia, Long> distribucionProvincias =
          HechosRepository.getInstance().getHechos().stream()
              .filter(Hecho::estaActivo)
              .filter(h -> h.getProvincia() != null)
              .collect(Collectors.groupingBy(Hecho::getProvincia, Collectors.counting()));

      List<Map<String, Object>> provincias =
          distribucionProvincias.entrySet().stream()
              .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
              .limit(5)
              .map(
                  entry -> {
                    Map<String, Object> prov = new HashMap<>();
                    prov.put("nombre", entry.getKey().toString());
                    prov.put("cantidad", entry.getValue());
                    return prov;
                  })
              .collect(Collectors.toList());

      model.put("distribucionProvincias", provincias);

      // Estado de solicitudes
      List<SolicitudEliminacion> solicitudes =
          SolicitudesEliminacionRepository.getInstance().getSolicitudes();
      long pendientes =
          solicitudes.stream()
              .filter(s -> s.getEstado() == EstadoSolicitudEliminacion.PENDIENTE)
              .count();
      long aprobadas =
          solicitudes.stream()
              .filter(s -> s.getEstado() == EstadoSolicitudEliminacion.APROBADO)
              .count();
      long rechazadas =
          solicitudes.stream()
              .filter(
                  s ->
                      s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO
                          || s.getEstado() == EstadoSolicitudEliminacion.RECHAZADO_AUTOMATICAMENTE)
              .count();

      model.put("solicitudesPendientes", pendientes);
      model.put("solicitudesAprobadas", aprobadas);
      model.put("solicitudesRechazadas", rechazadas);

      agregarInfoSesion(context, model);
      context.render("admin-estadisticas.hbs", model);

    } catch (Exception e) {
      e.printStackTrace();
      model.put("error", "Error al cargar las estadísticas: " + e.getMessage());
      agregarInfoSesion(context, model);
      context.render("admin-estadisticas.hbs", model);
    }
  }

  /** Lista todas las colecciones */
  public void listarColecciones(Context context) {
    // Verificar permisos de admin
    if (!esAdmin(context)) {
      context.redirect("/");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    try {
      List<Coleccion> colecciones = new ColeccionRepository().listar();

      // Preparar datos para el template
      List<Map<String, Object>> coleccionesParaTemplate =
          colecciones.stream()
              .map(
                  c -> {
                    Map<String, Object> coleccionMap = new HashMap<>();
                    coleccionMap.put("id", c.getId());
                    coleccionMap.put("titulo", c.getTitulo());
                    coleccionMap.put("descripcion", c.getDescripcion());
                    coleccionMap.put("categoria", c.getCategoria());
                    coleccionMap.put("cantidadHechos", c.getHechos().size());
                    return coleccionMap;
                  })
              .collect(Collectors.toList());

      model.put("colecciones", coleccionesParaTemplate);
      model.put("totalColecciones", colecciones.size());

      // Para mensajes de feedback
      String mensaje = context.queryParam("mensaje");
      if (mensaje != null) {
        model.put("mensajeCreada", mensaje.equals("creada"));
      }
      model.put("error", context.queryParam("error") != null);

      agregarInfoSesion(context, model);
      context.render("admin-colecciones.hbs", model);

    } catch (Exception e) {
      e.printStackTrace();
      model.put("error", "Error al cargar colecciones: " + e.getMessage());
      agregarInfoSesion(context, model);
      context.render("admin-colecciones.hbs", model);
    }
  }

  /** Muestra el formulario para crear una nueva colección */
  public void mostrarFormularioColeccion(Context context) {
    // Verificar permisos de admin
    if (!esAdmin(context)) {
      context.redirect("/");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    try {
      // Obtener fuentes disponibles
      List<Fuente> fuentes =
          entityManager().createQuery("from Fuente", Fuente.class).getResultList();

      List<Map<String, Object>> fuentesParaTemplate =
          fuentes.stream()
              .map(
                  f -> {
                    Map<String, Object> fuenteMap = new HashMap<>();
                    fuenteMap.put("id", f.getId());
                    String nombreFuente = f.getClass().getSimpleName().replace("Fuente", "");
                    fuenteMap.put("nombre", "Fuente " + nombreFuente + " (ID: " + f.getId() + ")");
                    return fuenteMap;
                  })
              .collect(Collectors.toList());

      // Obtener categorías únicas de los hechos existentes
      List<String> categorias =
          HechosRepository.getInstance().getHechos().stream()
              .map(Hecho::getCategoria)
              .distinct()
              .sorted()
              .collect(Collectors.toList());

      model.put("fuentes", fuentesParaTemplate);
      model.put("categorias", categorias);
      agregarInfoSesion(context, model);
      context.render("admin-coleccion-crear.hbs", model);

    } catch (Exception e) {
      e.printStackTrace();
      context.redirect("/admin/colecciones?error=true");
    }
  }

  /** Crea una nueva colección */
  public void crearColeccion(Context context) {
    // Verificar permisos de admin
    if (!esAdmin(context)) {
      context.redirect("/");
      return;
    }

    try {
      // Obtener datos del formulario
      String titulo = context.formParam("titulo");
      String descripcion = context.formParam("descripcion");
      Long fuenteId = Long.parseLong(context.formParam("fuenteId"));
      String localidad = context.formParam("localidad");
      String fechaInicialStr = context.formParam("fechaInicial");
      String fechaFinalStr = context.formParam("fechaFinal");
      String categoria = context.formParam("categoria");

      // Validar campos obligatorios
      if (titulo == null
          || titulo.trim().isEmpty()
          || fuenteId == null
          || fechaInicialStr == null
          || fechaFinalStr == null
          || categoria == null
          || categoria.trim().isEmpty()) {
        context.redirect("/admin/colecciones/crear?error=campos_requeridos");
        return;
      }

      // Parsear fechas
      LocalDate fechaInicial = LocalDate.parse(fechaInicialStr);
      LocalDate fechaFinal = LocalDate.parse(fechaFinalStr);

      // Obtener fuente
      Fuente fuente = entityManager().find(Fuente.class, fuenteId);
      if (fuente == null) {
        context.redirect("/admin/colecciones/crear?error=fuente_no_encontrada");
        return;
      }

      // Crear colección
      Coleccion coleccion =
          new Coleccion(
              titulo.trim(),
              descripcion != null ? descripcion.trim() : "",
              fuente,
              localidad != null ? localidad.trim() : "",
              fechaInicial,
              fechaFinal,
              categoria.trim(),
              null // algoritmo de consenso opcional por ahora
              );

      // Persistir
      withTransaction(
          () -> {
            new ColeccionRepository().persistir(coleccion);
            coleccion.agregarHechos();
          });

      context.redirect("/admin/colecciones?mensaje=creada");

    } catch (DateTimeParseException e) {
      e.printStackTrace();
      context.redirect("/admin/colecciones/crear?error=fecha_invalida");
    } catch (NumberFormatException e) {
      e.printStackTrace();
      context.redirect("/admin/colecciones/crear?error=datos_invalidos");
    } catch (Exception e) {
      e.printStackTrace();
      context.redirect("/admin/colecciones/crear?error=true");
    }
  }
}
