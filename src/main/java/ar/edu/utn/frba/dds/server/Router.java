package ar.edu.utn.frba.dds.server;

import ar.edu.utn.frba.dds.controllers.AuthController;
import ar.edu.utn.frba.dds.controllers.HechosController;
import io.javalin.Javalin;
import java.util.HashMap;
import java.util.Map;

public class Router {
    public void configure(Javalin app) {
        AuthController authController = new AuthController();
        HechosController hechosController = new HechosController();

        // Rutas de autenticación (GET y POST manejados en el mismo método)
        app.get("/login", authController::login);
        app.post("/login", authController::login);

        app.get("/register", authController::register);
        app.post("/register", authController::register);

        app.get("/logout", authController::logout);

        // Rutas de hechos
        app.get("/hechos", hechosController::listarHechos);
        app.get("/hechos/{id}", hechosController::verDetalle);

        //Gestor de solicitudes
        app.get("/hechos/crear", hechosController::mostrarFormularioCrear);
        app.post("/hechos/crear", hechosController::crearSolicitudDeAgregacion);
        app.get("/hechos/eliminar", hechosController::mostrarFormularioEliminar);
        app.post("/hechos/eliminar", hechosController::crearSolicitudDeEliminacion);

        app.get("/solicitudes",hechosController::mostrarSolicitudes);

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
