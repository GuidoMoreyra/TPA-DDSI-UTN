package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.contracts.Solicitud;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudAgregacion;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.enums.Operacion;
import ar.edu.utn.frba.dds.enums.OrigenHecho;
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
            hechos = hechos.stream()
                    .filter(h -> h.getProvincia() != null && h.getProvincia().toString().equalsIgnoreCase(provinciaFiltro))
                    .collect(Collectors.toList());
        }

        if (busqueda != null && !busqueda.trim().isEmpty()) {
            String busquedaLower = busqueda.toLowerCase().trim();
            hechos = hechos.stream()
                    .filter(h ->
                            (h.getTitulo() != null && h.getTitulo().toLowerCase().contains(busquedaLower)) ||
                            (h.getDescripcion() != null && h.getDescripcion().toLowerCase().contains(busquedaLower))
                    )
                    .collect(Collectors.toList());
        }

        // Preparar hechos con descripción truncada
        List<Map<String, Object>> hechosParaTemplate = hechos.stream().map(h -> {
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
        }).collect(Collectors.toList());

        // Obtener categorías únicas para el filtro
        List<String> categorias = HechosRepository.getInstance().getHechos()
                .stream()
                .map(Hecho::getCategoria)
                .filter(c -> c != null && !c.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // Obtener provincias únicas para el filtro
        List<String> provincias = HechosRepository.getInstance().getHechos()
                .stream()
                .map(h -> h.getProvincia() != null ? h.getProvincia().toString() : null)
                .filter(p -> p != null)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        // Preparar categorías con flag de selección
        List<Map<String, Object>> categoriasConSeleccion = categorias.stream().map(c -> {
            Map<String, Object> catMap = new HashMap<>();
            catMap.put("nombre", c);
            catMap.put("selected", c.equals(categoriaFiltro));
            return catMap;
        }).collect(Collectors.toList());

        // Preparar provincias con flag de selección
        List<Map<String, Object>> provinciasConSeleccion = provincias.stream().map(p -> {
            Map<String, Object> provMap = new HashMap<>();
            provMap.put("nombre", p);
            provMap.put("selected", p.equals(provinciaFiltro));
            return provMap;
        }).collect(Collectors.toList());

        // Preparar datos para el template
        model.put("hechos", hechosParaTemplate);
        model.put("categorias", categoriasConSeleccion);
        model.put("provincias", provinciasConSeleccion);
        model.put("totalHechos", hechos.size());

        // Mantener filtros seleccionados
        model.put("todasCategoriasSelected", categoriaFiltro == null || categoriaFiltro.equals("todas"));
        model.put("todasProvinciasSelected", provinciaFiltro == null || provinciaFiltro.equals("todas"));
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
            Hecho hecho = HechosRepository.getInstance().getHechos()
                    .stream()
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
                    model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);
                }

                context.render("hecho-detalle.hbs", model);
            } else {
                context.redirect("/hechos");
            }
        } catch (NumberFormatException e) {
            context.redirect("/hechos");
        }
    }

    public void mostrarSolicitudes(Context context){
        String userIdStr = context.formParam("user_id");
        String userAccesStr = context.sessionAttribute("nivel_acceso");
        if(userIdStr == null){
            context.redirect("/login");
        }
        int userAcces = 1;
        try {
            if (userAccesStr != null) {
                userAcces = Integer.parseInt(userAccesStr);
            }
        } catch (NumberFormatException e) {

        }
        List<SolicitudAgregacion> solAgre =
                SolicitudesAgregacionRepository
                        .getInstance()
                        .obtenerSolicitudesConEstado(EstadoSolicitudAgregacion.PENDIENTE)
                        .stream()
                        .toList();

        List<SolicitudEliminacion> solElim =
                SolicitudesEliminacionRepository
                        .getInstance()
                        .obtenerSolicitudesConEstado(EstadoSolicitudEliminacion.PENDIENTE)
                        .stream()
                        .toList();

        Map<String, Object> model = new HashMap<>();

        model.put("solicitudesA",solAgre);
        model.put("solicitudesE",solElim);

        context.render("gestos-solicitudes.hbs");
        


    }

    public void mostrarFormularioCrear(Context context) {
        // Verificar que el usuario esté logueado
        if (context.sessionAttribute("user_id") == null) {
            context.redirect("/login");
            return;
        }

        Map<String, Object> model = new HashMap<>();

        // Obtener categorías existentes
        List<String> categorias = HechosRepository.getInstance().getHechos()
                .stream()
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

    public void mostrarFormularioEliminar(@NotNull Context context) {
        // Verificar que el usuario esté logueado
        if (context.sessionAttribute("user_id") == null) {
            context.redirect("/login");
            return;
        }

        Map<String, Object> model = new HashMap<>();

        // Info de sesión para el navbar
        model.put("user", true);
        model.put("nombre", context.sessionAttribute("nombre"));
        Integer nivelAcceso = context.sessionAttribute("nivel_acceso");
        model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);

        context.render("crear-hecho.hbs", model);
    }

    public void crearSolicitudDeAgregacion(Context context) {

        //El usuario debe estar logeado para subir contenido multimedia.
//        if(context.sessionAttribute("user_id") != null){
//            UploadedFile file = context.uploadedFile("imagen");
//            var rutaImg = ImgManger.Guardar();
//        } else {
//
//        }

        //El usuario no requiere estar logeado para crear hechos.

        try {
            // Obtener datos del formulario
            String titulo = context.formParam("titulo");
            String descripcion = context.formParam("descripcion");
            String categoria = context.formParam("categoria");
            String latitudStr = context.formParam("latitud");
            String longitudStr = context.formParam("longitud");
            String fechaHechoStr = context.formParam("fechaHecho");
            String horaHechoStr = context.formParam("horaHecho");

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

            // Crear el hecho
            Hecho nuevoHecho = new Hecho(
                titulo.trim(),
                descripcion.trim(),
                categoria.trim(),
                latitud,
                longitud,
                fechaHecho,
                OrigenHecho.DINAMICO,
                null, // contenido multimedia (por ahora null)
                horaHecho
            );
            Usuario usuario = UsuarioRepository.getInstance().getUsuarioById(context.sessionAttribute("user_id"));
            var nuevaSolicitudDeAgregacion = new SolicitudAgregacion(nuevoHecho, usuario);

            SolicitudesAgregacionRepository.getInstance().persist(nuevaSolicitudDeAgregacion);


            // Redirigir al formulario
            this.mostrarFormularioCrear(context);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarError(context, "Error al crear la solicitud de agregacion: " + e.getMessage());
        }
    }

    public void crearSolicitudDeEliminacion(Context context){
        try{
            Hecho hecho = HechosRepository.getInstance().getHechoById(Long.valueOf(context.formParam("HechoId")));

            Map<String, Object> model = new HashMap<>();

            if(hecho == null){
                model.put("error","El hecho no existe");
                return;
            }

            var nuevaSolicitudDeEliminacion = new SolicitudEliminacion(
                    hecho,
                    context.formParam("justificacion"),
                    null //Deberia inyectarse por dependencia.
            );
            SolicitudesEliminacionRepository.getInstance().agregarSolicitud(nuevaSolicitudDeEliminacion);
            context.render("") ;// Hay qued definir la vista :D
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void cambiarEstadoSolicitudDeAgregacion(Context context) {

        String userIdStr = context.formParam("user_id");
        String userAccesStr = context.sessionAttribute("nivel_acceso");
        if(userIdStr == null){
            context.redirect("/login");
        }
        int userAcces = 1;
        try {
            if (userAccesStr != null) {
                userAcces = Integer.parseInt(userAccesStr);
            }
        } catch (NumberFormatException e) {

        }

        if( userAcces == 1 ) context.redirect("/home");


        String solicitudIdStr = context.formParam("solicitudId");
        String operacionStr = context.formParam("Operacion");

        if (solicitudIdStr == null || operacionStr == null) {
            // Manejar error: parámetros faltantes
            return;
        }

        Long solicitudId = Long.parseLong(solicitudIdStr);

        // Buscar la solicitud (mejor con query directa en repo)
        SolicitudAgregacion solicitud = SolicitudesAgregacionRepository.getInstance()
                .getSolicitudes()
                .stream()
                .filter(s -> s.getId() == solicitudId )
                .findFirst()
                .orElse(null);

        Map<String, Object> model = new HashMap<>();

        if(solicitud == null){
            model.put("error","La solicitud no existe");
            return;
        }

        Operacion operacion;
        try {
            operacion = Operacion.valueOf(operacionStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return;
        }
        operacion.ejecutar(solicitud);

    }

    public void cambiarEstadoSolicitudDeEliminacion(Context context) {
        String userIdStr = context.formParam("user_id");
        String userAccesStr = context.sessionAttribute("nivel_acceso");
        if(userIdStr == null){
            context.redirect("/login");
        }
        int userAcces = 1;
        try {
            if (userAccesStr != null) {
                userAcces = Integer.parseInt(userAccesStr);
            }
        } catch (NumberFormatException e) {

        }

        if( userAcces == 1 ) context.redirect("/home");

        String solicitudIdStr = context.formParam("solicitudId");
        String operacionStr = context.formParam("Operacion");

        if (solicitudIdStr == null || operacionStr == null) {
            // Manejar error: parámetros faltantes
            return;
        }

        Long solicitudId = Long.parseLong(solicitudIdStr);

        // Buscar la solicitud (mejor con query directa en repo)
        SolicitudEliminacion solicitud = SolicitudesEliminacionRepository.getInstance()
                .getSolicitudes()
                .stream()
                .filter(s -> s.getId() == solicitudId )
                .findFirst()
                .orElse(null);

        Map<String, Object> model = new HashMap<>();

        if(solicitud == null){
            model.put("error","La solicitud no existe");
            return;
        }

        Operacion operacion;
        try {
            operacion = Operacion.valueOf(operacionStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return;
        }
        operacion.ejecutar(solicitud);

    }



    private void mostrarError(Context context, String mensaje) {
        Map<String, Object> model = new HashMap<>();

        // Obtener categorías para mostrar el formulario nuevamente
        List<String> categorias = HechosRepository.getInstance().getHechos()
                .stream()
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
}
