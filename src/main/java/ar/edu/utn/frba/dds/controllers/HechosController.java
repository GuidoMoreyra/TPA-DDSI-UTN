package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.contracts.Solicitud;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.Operacion;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.DetectorDeSpamBasico;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.SolicitudAgregacion;
import ar.edu.utn.frba.dds.models.SolicitudEliminacion;
import ar.edu.utn.frba.dds.models.Usuario;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesAgregacionRepository;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import ar.edu.utn.frba.dds.repositories.UsuarioRepository;
import ar.edu.utn.frba.dds.utils.ImgManager;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import io.javalin.http.Context;
import io.javalin.http.UploadedFile;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HechosController implements WithSimplePersistenceUnit {

    private ImgManager imgManager;



    public void listarHechos(Context context) {
        Map<String, Object> model = new HashMap<>();

        // Obtener parámetros de filtro opcionales
        String categoriaFiltro = context.queryParam("categoria");
        String provinciaFiltro = context.queryParam("provincia");
        String busqueda = context.queryParam("busqueda");

        // Obtener todos los hechos activos
        List<Hecho> hechos = HechosRepository.getInstance().getHechos()
                .stream()
                .filter(Hecho::estaActivo)
                .collect(Collectors.toList());

        // Aplicar filtros si existen
        if (categoriaFiltro != null && !categoriaFiltro.isEmpty() && !categoriaFiltro.equals("todas")) {
            hechos = hechos.stream()
                    .filter(h -> categoriaFiltro.equalsIgnoreCase(h.getCategoria()))
                    .collect(Collectors.toList());
        }

    if (provinciaFiltro != null && !provinciaFiltro.isEmpty() && !provinciaFiltro.equals("todas")) {
      hechos =
          hechos.stream()
              .filter(
                  h ->
                      h.getProvincia() != null
                          && h.getProvincia().toString().equalsIgnoreCase(provinciaFiltro))
              .collect(Collectors.toList());
    }

    if (busqueda != null && !busqueda.trim().isEmpty()) {
      String busquedaLower = busqueda.toLowerCase().trim();
      hechos =
          hechos.stream()
              .filter(
                  h ->
                      (h.getTitulo() != null && h.getTitulo().toLowerCase().contains(busquedaLower))
                          || (h.getDescripcion() != null
                              && h.getDescripcion().toLowerCase().contains(busquedaLower)))
              .collect(Collectors.toList());
    }

    // Preparar hechos con descripción truncada
    List<Map<String, Object>> hechosParaTemplate =
        hechos.stream()
            .map(
                h -> {
                  Map<String, Object> hechoMap = new HashMap<>();
                  hechoMap.put("id", h.getId());
                  hechoMap.put("titulo", h.getTitulo());
                  hechoMap.put("categoria", h.getCategoria());
                  hechoMap.put("provincia", h.getProvincia());
                  hechoMap.put("fechaDelHecho", h.getFechaDelHecho());
                  hechoMap.put("coordenadas", h.getCoordenadas());

                  // Truncar descripción si es muy larga
                  String descripcion = h.getDescripcion();
                  if (descripcion != null && descripcion.length() > 80) {
                    hechoMap.put("descripcionTruncada", descripcion.substring(0, 80) + "...");
                  } else {
                    hechoMap.put("descripcionTruncada", descripcion);
                  }

                  return hechoMap;
                })
            .collect(Collectors.toList());

    // Obtener categorías únicas para el filtro
    List<String> categorias =
        HechosRepository.getInstance().getHechos().stream()
            .map(Hecho::getCategoria)
            .filter(c -> c != null && !c.isEmpty())
            .distinct()
            .sorted()
            .collect(Collectors.toList());

    // Obtener provincias únicas para el filtro
    List<String> provincias =
        HechosRepository.getInstance().getHechos().stream()
            .map(h -> h.getProvincia() != null ? h.getProvincia().toString() : null)
            .filter(p -> p != null)
            .distinct()
            .sorted()
            .collect(Collectors.toList());

    // Preparar categorías con flag de selección
    List<Map<String, Object>> categoriasConSeleccion =
        categorias.stream()
            .map(
                c -> {
                  Map<String, Object> catMap = new HashMap<>();
                  catMap.put("nombre", c);
                  catMap.put("selected", c.equals(categoriaFiltro));
                  return catMap;
                })
            .collect(Collectors.toList());

    // Preparar provincias con flag de selección
    List<Map<String, Object>> provinciasConSeleccion =
        provincias.stream()
            .map(
                p -> {
                  Map<String, Object> provMap = new HashMap<>();
                  provMap.put("nombre", p);
                  provMap.put("selected", p.equals(provinciaFiltro));
                  return provMap;
                })
            .collect(Collectors.toList());

    // Preparar datos para el template
    model.put("hechos", hechosParaTemplate);
    model.put("categorias", categoriasConSeleccion);
    model.put("provincias", provinciasConSeleccion);
    model.put("totalHechos", hechos.size());

    // Mantener filtros seleccionados
    model.put(
        "todasCategoriasSelected", categoriaFiltro == null || categoriaFiltro.equals("todas"));
    model.put(
        "todasProvinciasSelected", provinciaFiltro == null || provinciaFiltro.equals("todas"));
    model.put("busquedaActual", busqueda != null ? busqueda : "");

    // Info de sesión para el navbar
    if (context.sessionAttribute("user_id") != null) {
      model.put("user", true);
      model.put("nombre", context.sessionAttribute("nombre"));
      Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
      model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);
    }

    context.render("hechos.hbs", model);
  }

  public void verDetalle(Context context) {
    Map<String, Object> model = new HashMap<>();

    try {
      Long id = Long.parseLong(context.pathParam("id"));
      Hecho hecho =
          HechosRepository.getInstance().getHechos().stream()
              .filter(h -> h.getId().equals(id))
              .findFirst()
              .orElse(null);

      if (hecho != null) {
        model.put("hecho", hecho);

        // Info de sesión
        if (context.sessionAttribute("user_id") != null) {
          model.put("user", true);
          model.put("nombre", context.sessionAttribute("nombre"));
          Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
          boolean esAdmin = nivelAcceso != null && nivelAcceso >= 1;
          model.put("isAdmin", esAdmin);

          // Solo admins pueden editar
          model.put("puedeEditar", esAdmin);
        }

        context.render("hecho-detalle.hbs", model);
      } else {
        context.redirect("/hechos");
      }
    } catch (NumberFormatException e) {
      context.redirect("/hechos");
    }
  }

  public void mostrarFormularioCrear(Context context) {
    // Verificar que el usuario esté logueado
    if (context.sessionAttribute("user_id") == null) {
      context.redirect("/login");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    // Obtener categorías existentes
    List<String> categorias =
        HechosRepository.getInstance().getHechos().stream()
            .map(Hecho::getCategoria)
            .filter(c -> c != null && !c.isEmpty())
            .distinct()
            .sorted()
            .collect(Collectors.toList());

    model.put("categorias", categorias);

    // Info de sesión para el navbar
    model.put("user", true);
    model.put("nombre", context.sessionAttribute("nombre"));
    Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
    model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);

    context.render("crear-hecho.hbs", model);
  }

  public void crearHecho(Context context) {
    // Verificar que el usuario esté logueado
    if (context.sessionAttribute("user_id") == null) {
      context.redirect("/login");
      return;
    }

    try {
      // Obtener datos del formulario
      String titulo = context.formParam("titulo");
      String descripcion = context.formParam("descripcion");
      String categoria = context.formParam("categoria");
      String latitudStr = context.formParam("latitud");
      String longitudStr = context.formParam("longitud");
      String fechaHechoStr = context.formParam("fechaHecho");
      final String horaHechoStr = context.formParam("horaHecho");
      final String contenidoMultimedia = context.formParam("contenidoMultimedia");

      // Validaciones básicas
      if (titulo == null || titulo.trim().isEmpty()) {
        mostrarError(context, "El título es obligatorio");
        return;
      }
      if (descripcion == null || descripcion.trim().isEmpty()) {
        mostrarError(context, "La descripción es obligatoria");
        return;
      }
      if (categoria == null || categoria.trim().isEmpty()) {
        mostrarError(context, "La categoría es obligatoria");
        return;
      }

      // Parsear coordenadas
      double latitud;
      double longitud;
      try {
        latitud = Double.parseDouble(latitudStr);
        longitud = Double.parseDouble(longitudStr);
      } catch (NumberFormatException e) {
        mostrarError(context, "Las coordenadas deben ser números válidos");
        return;
      }

      // Validar rango de coordenadas
      if (latitud < -90 || latitud > 90) {
        mostrarError(context, "La latitud debe estar entre -90 y 90");
        return;
      }
      if (longitud < -180 || longitud > 180) {
        mostrarError(context, "La longitud debe estar entre -180 y 180");
        return;
      }

      // Parsear fecha
      LocalDate fechaHecho;
      try {
        fechaHecho = LocalDate.parse(fechaHechoStr);
      } catch (Exception e) {
        mostrarError(context, "La fecha del hecho no es válida");
        return;
      }

      // Parsear hora (opcional)
      LocalTime horaHecho = null;
      if (horaHechoStr != null && !horaHechoStr.trim().isEmpty()) {
        try {
          horaHecho = LocalTime.parse(horaHechoStr);
        } catch (Exception e) {
          mostrarError(context, "La hora del hecho no es válida");
          return;
        }
      }

      // Procesar contenido multimedia (opcional)
      String contenidoMultimediaFinal = null;
      if (contenidoMultimedia != null && !contenidoMultimedia.trim().isEmpty()) {
        contenidoMultimediaFinal = contenidoMultimedia.trim();
      }

      // Crear el hecho
      Hecho nuevoHecho =
          new Hecho(
              titulo.trim(),
              descripcion.trim(),
              categoria.trim(),
              latitud,
              longitud,
              fechaHecho,
              OrigenHecho.DINAMICO,
              contenidoMultimediaFinal,
              horaHecho);

      // Persistir usando transacción
      withTransaction(
          () -> {
            HechosRepository.getInstance().agregarHecho(nuevoHecho);
          });

      // Redirigir al detalle del hecho creado
      context.redirect("/hechos/" + nuevoHecho.getId());

    } catch (Exception e) {
      e.printStackTrace();
      mostrarError(context, "Error al crear el hecho: " + e.getMessage());
    }
  }

  private void mostrarError(Context context, String mensaje) {
    Map<String, Object> model = new HashMap<>();

    // Obtener categorías para mostrar el formulario nuevamente
    List<String> categorias =
        HechosRepository.getInstance().getHechos().stream()
            .map(Hecho::getCategoria)
            .filter(c -> c != null && !c.isEmpty())
            .distinct()
            .sorted()
            .collect(Collectors.toList());

    model.put("categorias", categorias);
    model.put("error", mensaje);

    // Info de sesión para el navbar
    if (context.sessionAttribute("user_id") != null) {
      model.put("user", true);
      model.put("nombre", context.sessionAttribute("nombre"));
      Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
      model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);
    }

    context.render("crear-hecho.hbs", model);
  }

  public void reportarHecho(Context context) {
    // Verificar que el usuario esté logueado
    if (context.sessionAttribute("user_id") == null) {
      context.redirect("/login");
      return;
    }

    Hecho hecho = null;

    try {
      // Obtener el ID del hecho desde la URL
      Long hechoId = Long.parseLong(context.pathParam("id"));

      // Buscar el hecho en el repositorio
      hecho =
          HechosRepository.getInstance().getHechos().stream()
              .filter(h -> h.getId().equals(hechoId))
              .findFirst()
              .orElse(null);

      if (hecho == null) {
        context.redirect("/hechos");
        return;
      }

      // Obtener la justificación del formulario
      String justificacion = context.formParam("justificacion");

      // Validaciones
      if (justificacion == null || justificacion.trim().isEmpty()) {
        mostrarDetalleConError(context, hecho, "La justificación es obligatoria");
        return;
      }

      if (justificacion.trim().length() < 500) {
        mostrarDetalleConError(
            context,
            hecho,
            "La justificación debe tener al menos 500 caracteres. Actualmente tiene "
                + justificacion.trim().length()
                + " caracteres.");
        return;
      }

      // Crear la solicitud de eliminación
      DetectorDeSpamBasico detector = new DetectorDeSpamBasico();
      SolicitudEliminacion solicitud =
          new SolicitudEliminacion(hecho, justificacion.trim(), detector);

      // Persistir usando el repositorio
      withTransaction(
          () -> {
            SolicitudesEliminacionRepository.getInstance().agregarSolicitud(solicitud);
          });

      // Redirigir al detalle con mensaje de éxito
      mostrarDetalleConExito(context, hecho);

    } catch (IllegalArgumentException e) {
      // Capturar error de validación del modelo o error al parsear el ID
      if (hecho != null) {
        mostrarDetalleConError(context, hecho, e.getMessage());
      } else {
        // Si no tenemos el hecho, probablemente fue un error al parsear el ID
        context.redirect("/hechos");
      }
    } catch (Exception e) {
      // Cualquier otro error
      e.printStackTrace();
      if (hecho != null) {
        mostrarDetalleConError(context, hecho, "Error al procesar la solicitud: " + e.getMessage());
      } else {
        context.redirect("/hechos");
      }
    }
  }

  private void mostrarDetalleConError(Context context, Hecho hecho, String error) {
    Map<String, Object> model = new HashMap<>();
    model.put("hecho", hecho);
    model.put("errorReporte", error);

    // Info de sesión para el navbar
    if (context.sessionAttribute("user_id") != null) {
      model.put("user", true);
      model.put("nombre", context.sessionAttribute("nombre"));
      Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
      model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);
    }

    context.render("hecho-detalle.hbs", model);
  }

  private void mostrarDetalleConExito(Context context, Hecho hecho) {
    Map<String, Object> model = new HashMap<>();
    model.put("hecho", hecho);
    model.put("exitoReporte", true);

    // Info de sesión para el navbar
    if (context.sessionAttribute("user_id") != null) {
      model.put("user", true);
      model.put("nombre", context.sessionAttribute("nombre"));
      Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
      model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);
    }

    context.render("hecho-detalle.hbs", model);
  }

  public void mostrarFormularioEditar(Context context) {
    // Verificar que el usuario esté logueado y sea admin
    if (context.sessionAttribute("user_id") == null) {
      context.redirect("/login");
      return;
    }

    Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
    boolean esAdmin = nivelAcceso != null && nivelAcceso >= 1;

    if (!esAdmin) {
      context.redirect("/hechos");
      return;
    }

    try {
      Long hechoId = Long.parseLong(context.pathParam("id"));
      Hecho hecho = HechosRepository.getInstance().buscarPorId(hechoId);

      if (hecho == null) {
        context.redirect("/hechos");
        return;
      }

      Map<String, Object> model = new HashMap<>();
      model.put("hecho", hecho);

      // Obtener categorías existentes con flag de selección
      List<String> categoriasSimples =
          HechosRepository.getInstance().getHechos().stream()
              .map(Hecho::getCategoria)
              .filter(c -> c != null && !c.isEmpty())
              .distinct()
              .sorted()
              .collect(Collectors.toList());

      List<Map<String, Object>> categorias =
          categoriasSimples.stream()
              .map(
                  c -> {
                    Map<String, Object> catMap = new HashMap<>();
                    catMap.put("nombre", c);
                    catMap.put("selected", c.equals(hecho.getCategoria()));
                    return catMap;
                  })
              .collect(Collectors.toList());

      model.put("categorias", categorias);

      // Info de sesión para el navbar
      model.put("user", true);
      model.put("nombre", context.sessionAttribute("nombre"));
      model.put("isAdmin", true);

      context.render("hecho-editar.hbs", model);

    } catch (NumberFormatException e) {
      context.redirect("/hechos");
    }
  }

  public void editarHecho(Context context) {
    // Verificar que el usuario esté logueado y sea admin
    if (context.sessionAttribute("user_id") == null) {
      context.redirect("/login");
      return;
    }

    Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
    boolean esAdmin = nivelAcceso != null && nivelAcceso >= 1;

    if (!esAdmin) {
      context.redirect("/hechos");
      return;
    }

    try {
      Long hechoId = Long.parseLong(context.pathParam("id"));
      Hecho hecho = HechosRepository.getInstance().buscarPorId(hechoId);

      if (hecho == null) {
        context.redirect("/hechos");
        return;
      }

      // Obtener datos del formulario
      String titulo = context.formParam("titulo");
      String descripcion = context.formParam("descripcion");
      String categoria = context.formParam("categoria");
      final String latitudStr = context.formParam("latitud");
      final String longitudStr = context.formParam("longitud");
      final String urlMultimedia = context.formParam("urlMultimedia");

      // Validar campos obligatorios
      if (titulo == null || titulo.trim().isEmpty()) {
        throw new IllegalArgumentException("El título es obligatorio");
      }
      if (descripcion == null || descripcion.trim().isEmpty()) {
        throw new IllegalArgumentException("La descripción es obligatoria");
      }
      if (categoria == null || categoria.trim().isEmpty()) {
        throw new IllegalArgumentException("La categoría es obligatoria");
      }
      if (latitudStr == null || longitudStr == null) {
        throw new IllegalArgumentException("Las coordenadas son obligatorias");
      }

      // Parsear coordenadas
      double latitud = Double.parseDouble(latitudStr);
      double longitud = Double.parseDouble(longitudStr);

      // Crear DTO con los cambios
      CambiosHechoDto cambios = new CambiosHechoDto();
      cambios.setTitulo(titulo.trim());
      cambios.setDescripcion(descripcion.trim());
      cambios.setCategoria(categoria.trim());
      cambios.setCoordenadas(new Coordenada(latitud, longitud));

      // Actualizar contenido multimedia si se proporcionó
      if (urlMultimedia != null && !urlMultimedia.trim().isEmpty()) {
        cambios.setContenidoMultimedia(urlMultimedia.trim());
      }

      // Aplicar cambios al hecho
      hecho.aplicarCambios(cambios);

      // Guardar en base de datos
      withTransaction(
          () -> {
            HechosRepository.getInstance().actualizarHecho(hecho);
          });

      // Limpiar caché para asegurar que se lean los datos actualizados
      entityManager().clear();

      // Redirigir al detalle del hecho actualizado
      context.redirect("/hechos/" + hecho.getId());

    } catch (Exception e) {
      e.printStackTrace();
      context.redirect("/hechos");
    }
  }
}
