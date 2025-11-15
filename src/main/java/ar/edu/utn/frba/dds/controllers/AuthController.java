package ar.edu.utn.frba.dds.controllers;

import ar.edu.utn.frba.dds.models.Usuario;
import ar.edu.utn.frba.dds.repositories.UsuarioRepository;
import io.javalin.http.Context;
import java.util.HashMap;
import java.util.Map;

public class AuthController {

  public void login(Context context) {
    // Verifico si ya está logeado
    if (context.sessionAttribute("user_id") != null) {
      context.redirect("/");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    // Si es POST, intento autenticar
    if (context.method().toString().equals("POST")) {
      String nombre = context.formParam("nombre");
      String password = context.formParam("password");

      if (nombre != null && password != null && !nombre.isEmpty() && !password.isEmpty()) {
        Usuario usuario = UsuarioRepository.getInstance().getUsuario(nombre, password);
        if (usuario != null) {
          context.sessionAttribute("user_id", usuario.getId());
          context.sessionAttribute("nombre", usuario.getNombre());
          context.sessionAttribute("nivel_acceso", usuario.getNivelDeAcceso());
          context.redirect("/");
          return;
        } else {
          model.put("mensaje", "Usuario o contraseña incorrectos");
        }
      } else {
        model.put("mensaje", "Complete todos los campos");
      }
    }

    // Si es GET o si falló el POST, renderizo el login
    context.render("login.hbs", model);
  }

  public void register(Context context) {
    // Verifico si ya está logeado
    if (context.sessionAttribute("user_id") != null) {
      context.redirect("/");
      return;
    }

    Map<String, Object> model = new HashMap<>();

    // Si es POST, intento registrar
    if (context.method().toString().equals("POST")) {
      String nombre = context.formParam("nombre");
      String password = context.formParam("password");
      String passwordConfirm = context.formParam("passwordConfirm");

      if (nombre != null && password != null && passwordConfirm != null) {
        // Validaciones
        if (nombre.isEmpty()) {
          model.put("mensaje", "El nombre de usuario es requerido");
        } else if (password.length() < 8) {
          model.put("mensaje", "La contraseña debe tener al menos 8 caracteres");
        } else if (!password.equals(passwordConfirm)) {
          model.put("mensaje", "Las contraseñas no coinciden");
        } else {
          Usuario usuario = new Usuario(nombre, password);
          try {
            UsuarioRepository.INSTANCE.persistUsuario(usuario);
            context.sessionAttribute("user_id", usuario.getId());
            context.sessionAttribute("nombre", usuario.getNombre());
            context.sessionAttribute("nivel_acceso", usuario.getNivelDeAcceso());
            context.redirect("/");
            return;
          } catch (Exception e) {
            System.err.println("Error al persistir usuario: " + e.getMessage());
            model.put("mensaje", "Error al registrarse. Pruebe más tarde.");
          }
        }
      } else {
        model.put("mensaje", "Complete todos los campos");
      }
    }

    // Si es GET o si falló el POST, renderizo el registro
    context.render("register.hbs", model);
  }

  public void logout(Context context) {
    context.sessionAttribute("user_id", null);
    context.sessionAttribute("nombre", null);
    context.sessionAttribute("nivel_acceso", null);
    context.redirect("/login");
  }
}
