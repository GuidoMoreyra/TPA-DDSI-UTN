package ar.edu.utn.frba.dds.seeders;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.Usuario;
import ar.edu.utn.frba.dds.repositories.HechosRepository;
import ar.edu.utn.frba.dds.repositories.UsuarioRepository;
import ar.edu.utn.frba.dds.repositories.fuentes.FuenteDinamica;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import java.time.LocalDate;
import java.time.LocalTime;

public class DatabaseSeeder implements WithSimplePersistenceUnit {

  public void seed() {
    // Solo hacer seed si no hay hechos en la base de datos
    if (!HechosRepository.getInstance().getHechos().isEmpty()) {
      System.out.println("La base de datos ya tiene datos, omitiendo seed");
      return;
    }

    System.out.println("Iniciando seed de la base de datos...");

    crearFuentesDeDatos();
    crearUsuariosDeEjemplo();
    crearHechosDeEjemplo();

    System.out.println("Seed completado exitosamente");
  }

  private void crearFuentesDeDatos() {
    System.out.println("Creando fuentes de datos de ejemplo...");

    withTransaction(
        () -> {
          // Crear fuente dinámica (de hechos contribuidos por usuarios)
          FuenteDinamica fuenteDinamica = new FuenteDinamica();
          entityManager().persist(fuenteDinamica);
          System.out.println("  - Fuente Dinámica creada");
        });
  }

  private void crearUsuariosDeEjemplo() {
    System.out.println("Creando usuarios de ejemplo...");

    // Crear contribuyentes de ejemplo
    String[][] usuarios = {
      {"maria.garcia", "password123"},
      {"juan.perez", "password123"},
      {"ana.rodriguez", "password123"}
    };

    for (String[] userData : usuarios) {
      Usuario usuario = UsuarioRepository.INSTANCE.getUsuarioPorNombre(userData[0]);
      if (usuario == null) {
        Usuario nuevoUsuario = new Usuario(userData[0], userData[1]);
        nuevoUsuario.setNivelDeAcceso(0); // Contribuyente
        UsuarioRepository.INSTANCE.persistUsuario(nuevoUsuario);
        System.out.println("  - Usuario creado: " + userData[0]);
      }
    }
  }

  private void crearHechosDeEjemplo() {
    System.out.println("Creando hechos de ejemplo...");

    // Hechos de diferentes categorías y provincias
    Object[][] hechos = {
      // Buenos Aires
      {
        "Protesta en Plaza de Mayo",
        "Manifestación pacífica de trabajadores en Plaza de Mayo. Miles de personas se reunieron"
            + " para reclamar mejoras salariales y condiciones laborales.",
        "Protestas",
        -34.6083,
        -58.3712,
        LocalDate.now().minusDays(2),
        LocalTime.of(15, 30),
        "https://images.unsplash.com/photo-1529107386315-e1a2ed48a620?w=800" // Imagen de protesta
      },
      {
        "Inundación en La Plata",
        "Fuertes lluvias causaron anegamientos en varias zonas de La Plata. Los vecinos reportan"
            + " calles cortadas y problemas de drenaje.",
        "Desastres Naturales",
        -34.9205,
        -57.9536,
        LocalDate.now().minusDays(5),
        LocalTime.of(8, 15),
        null
      },
      {
        "Festival de música en Parque Centenario",
        "Gran convocatoria en el festival de música indie realizado en Parque Centenario con más de"
            + " 10 bandas locales.",
        "Eventos Culturales",
        -34.6058,
        -58.4332,
        LocalDate.now().minusDays(1),
        LocalTime.of(18, 0),
        "https://www.youtube.com/watch?v=jNQXAC9IVRw" // Video de ejemplo (Me at the zoo - primer
        // video de YouTube)
      },

      // Córdoba
      {
        "Corte de ruta en Villa Carlos Paz",
        "Vecinos cortaron la ruta Provincial  en reclamo por el mal estado del camino. El tráfico"
            + " estuvo interrumpido por 3 horas.",
        "Cortes de Ruta",
        -31.4135,
        -64.4978,
        LocalDate.now().minusDays(3),
        LocalTime.of(10, 0),
        null
      },
      {
        "Inauguración de centro cultural",
        "Se inauguró un nuevo centro cultural en barrio Güemes con talleres gratuitos para la"
            + " comunidad.",
        "Eventos Culturales",
        -31.4073,
        -64.1852,
        LocalDate.now().minusDays(7),
        LocalTime.of(17, 30),
        "https://images.unsplash.com/photo-1540575467063-178a50c2df87?w=800" // Imagen de centro
        // cultural
      },

      // Santa Fe
      {
        "Crecida del río Paraná",
        "El río Paraná alcanzó niveles de alerta en Rosario. Autoridades monitorean la situación"
            + " constantemente.",
        "Desastres Naturales",
        -32.9442,
        -60.6505,
        LocalDate.now().minusDays(4),
        LocalTime.of(7, 0),
        "https://images.unsplash.com/photo-1547683905-f686c993aae5?w=800" // Imagen de inundación
      },
      {
        "Feria de artesanos en peatonal",
        "Gran concurrencia en la feria de artesanos locales realizada en la peatonal Córdoba.",
        "Eventos Culturales",
        -32.9468,
        -60.6393,
        LocalDate.now().minusDays(1),
        LocalTime.of(16, 0),
        null
      },

      // Mendoza
      {
        "Protesta de bodegueros",
        "Productores vitivinícolas protestaron frente a Casa de Gobierno por políticas"
            + " impositivas.",
        "Protestas",
        -32.8908,
        -68.8272,
        LocalDate.now().minusDays(6),
        LocalTime.of(11, 0),
        null
      },
      {
        "Terremoto de baja intensidad",
        "Se registró un sismo de 3.2 grados en la escala de Richter sin daños materiales ni"
            + " personales.",
        "Desastres Naturales",
        -32.8833,
        -68.8167,
        LocalDate.now().minusDays(10),
        LocalTime.of(3, 45),
        null
      },

      // Tucumán
      {
        "Marcha por el Día de la Independencia",
        "Multitudinaria marcha cívica en conmemoración del Día de la Independencia Argentina.",
        "Eventos Culturales",
        -26.8241,
        -65.2104,
        LocalDate.now().minusDays(30),
        LocalTime.of(10, 0),
        "https://www.youtube.com/watch?v=MU2O7CzqyDw" // Video histórico de ejemplo
      },
      {
        "Accidente de tránsito en Av. Mate de Luna",
        "Choque múltiple en Avenida Mate de Luna durante hora pico. Tres vehículos involucrados,"
            + " solo daños materiales.",
        "Accidentes",
        -26.8206,
        -65.2044,
        LocalDate.now().minusDays(1),
        LocalTime.of(19, 15),
        null
      },

      // Salta
      {
        "Festividad del Milagro",
        "Miles de fieles participaron de la tradicional procesión del Señor y la Virgen del"
            + " Milagro.",
        "Eventos Culturales",
        -24.7859,
        -65.4117,
        LocalDate.now().minusDays(15),
        LocalTime.of(9, 0),
        "https://images.unsplash.com/photo-1544928147-79a2dbc1f389?w=800" // Imagen religiosa
      },

      // Entre Ríos
      {
        "Avistamiento de fauna en Parque Nacional",
        "Turistas reportan avistamiento de carpinchos y aves autóctonas en el Parque Nacional El"
            + " Palmar.",
        "Medio Ambiente",
        -31.8628,
        -58.2629,
        LocalDate.now().minusDays(8),
        LocalTime.of(14, 30),
        "https://images.unsplash.com/photo-1437622368342-7a3d73a34c8f?w=800" // Imagen de naturaleza
      }
    };

    for (Object[] hechoData : hechos) {
      withTransaction(
          () -> {
            Hecho hecho =
                new Hecho(
                    (String) hechoData[0], // título
                    (String) hechoData[1], // descripción
                    (String) hechoData[2], // categoría
                    (Double) hechoData[3], // latitud
                    (Double) hechoData[4], // longitud
                    (LocalDate) hechoData[5], // fecha
                    OrigenHecho.DINAMICO, // origen
                    (String) hechoData[7], // contenido multimedia
                    (LocalTime) hechoData[6] // hora
                    );
            HechosRepository.getInstance().agregarHecho(hecho);
          });
      System.out.println("  - Hecho creado: " + hechoData[0]);
    }
  }
}
