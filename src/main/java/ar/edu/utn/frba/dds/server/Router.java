package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.AdminController;
import ar.edu.utn.frba.dds.controllers.AuthController;
import ar.edu.utn.frba.dds.controllers.HechosController;
import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;

public class Router {
    public void configure(Javalin app) {
        AuthController authController = new AuthController();
        HechosController hechosController = new HechosController();
        AdminController adminController = new AdminController();

        // Rutas de autenticación (GET y POST manejados en el mismo método)
        app.get("/login", authController::login);
        app.post("/login", authController::login);

        app.get("/register", authController::register);
        app.post("/register", authController::register);

        app.get("/logout", authController::logout);

        // Rutas de hechos
        app.get("/hechos", hechosController::listarHechos);
        app.get("/hechos/crear", hechosController::mostrarFormularioCrear);
        app.post("/hechos/crear", hechosController::crearHecho);
        app.get("/hechos/{id}", hechosController::verDetalle);
        app.get("/hechos/{id}/editar", hechosController::mostrarFormularioEditar);
        app.post("/hechos/{id}/editar", hechosController::editarHecho);
        app.post("/hechos/{id}/reportar", hechosController::reportarHecho);

        // Rutas del panel de administración
        app.get("/admin", ctx -> ctx.redirect("/admin/solicitudes"));
        app.get("/admin/solicitudes", adminController::listarSolicitudes);
        app.get("/admin/solicitudes/{id}", adminController::verDetalleSolicitud);
        app.post("/admin/solicitudes/{id}/aprobar", adminController::aprobarSolicitud);
        app.post("/admin/solicitudes/{id}/rechazar", adminController::rechazarSolicitud);
        app.get("/admin/estadisticas", adminController::verEstadisticas);

        // Ruta principal
        app.get("/", ctx -> {
            Map<String, Object> model = new HashMap<>();

            if (ctx.sessionAttribute("user_id") != null) {
                model.put("user", true);
                model.put("nombre", ctx.sessionAttribute("nombre"));
                Integer nivelAcceso = ctx.sessionAttribute("nivel_acceso");
                model.put("isAdmin", nivelAcceso != null && nivelAcceso >= 1);
            }

            ctx.render("index.hbs", model);
        });
    }
}
