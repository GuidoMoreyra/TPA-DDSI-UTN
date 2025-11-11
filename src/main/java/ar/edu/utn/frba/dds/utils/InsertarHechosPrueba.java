package ar.edu.utn.frba.dds.utils;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.models.Hecho;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import java.time.LocalDate;
import java.time.LocalTime;

public class InsertarHechosPrueba implements WithSimplePersistenceUnit {

    public static void main(String[] args) {
        InsertarHechosPrueba insertar = new InsertarHechosPrueba();
        insertar.insertarHechos();
    }

    public void insertarHechos() {
        System.out.println("Insertando hechos de prueba...");

        withTransaction(() -> {
            // Hecho 1: Histórico
            Hecho hecho1 = new Hecho(
                "Declaración de la Independencia Argentina",
                "El 9 de julio de 1816, el Congreso de Tucumán declaró la independencia de las Provincias Unidas en Sud América.",
                "Histórico",
                -26.8241, -65.2226, // Tucumán
                LocalDate.of(1816, 7, 9),
                OrigenHecho.DINAMICO,
                null,
                LocalTime.of(12, 0)
            );

            // Hecho 2: Cultural
            Hecho hecho2 = new Hecho(
                "Fundación del Teatro Colón",
                "El Teatro Colón de Buenos Aires fue inaugurado el 25 de mayo de 1908, siendo uno de los teatros de ópera más importantes del mundo.",
                "Cultural",
                -34.6011, -58.3836, // Buenos Aires
                LocalDate.of(1908, 5, 25),
                OrigenHecho.DINAMICO,
                null,
                LocalTime.of(20, 30)
            );

            // Hecho 3: Turístico
            Hecho hecho3 = new Hecho(
                "Cataratas del Iguazú declaradas Patrimonio Natural",
                "En 1984, las Cataratas del Iguazú fueron declaradas Patrimonio Natural de la Humanidad por la UNESCO.",
                "Turístico",
                -25.6953, -54.4367, // Misiones
                LocalDate.of(1984, 11, 1),
                OrigenHecho.DINAMICO,
                null,
                LocalTime.of(10, 0)
            );

            // Hecho 4: Histórico
            Hecho hecho4 = new Hecho(
                "Fundación de Buenos Aires",
                "Pedro de Mendoza fundó por primera vez Buenos Aires el 3 de febrero de 1536.",
                "Histórico",
                -34.6037, -58.3816, // Buenos Aires
                LocalDate.of(1536, 2, 3),
                OrigenHecho.DINAMICO,
                null,
                LocalTime.of(14, 0)
            );

            // Hecho 5: Cultural
            Hecho hecho5 = new Hecho(
                "Creación del Tango",
                "El tango nació en los arrabales de Buenos Aires a finales del siglo XIX como expresión cultural del Río de la Plata.",
                "Cultural",
                -34.6118, -58.3797, // Buenos Aires (La Boca)
                LocalDate.of(1880, 1, 1),
                OrigenHecho.DINAMICO,
                null,
                LocalTime.of(22, 0)
            );

            // Hecho 6: Turístico
            Hecho hecho6 = new Hecho(
                "Inauguración del Glaciar Perito Moreno como atractivo turístico",
                "El Glaciar Perito Moreno en Santa Cruz se convirtió en uno de los principales atractivos turísticos de la Patagonia argentina.",
                "Turístico",
                -50.4950, -73.1372, // Santa Cruz
                LocalDate.of(1945, 1, 15),
                OrigenHecho.DINAMICO,
                null,
                LocalTime.of(11, 30)
            );

            // Hecho 7: Histórico
            Hecho hecho7 = new Hecho(
                "Batalla de San Lorenzo",
                "El 3 de febrero de 1813, el General San Martín obtuvo su primera victoria en el Combate de San Lorenzo.",
                "Histórico",
                -32.7500, -60.7333, // Santa Fe
                LocalDate.of(1813, 2, 3),
                OrigenHecho.DINAMICO,
                null,
                LocalTime.of(9, 0)
            );

            // Hecho 8: Cultural
            Hecho hecho8 = new Hecho(
                "Declaración de la Quebrada de Humahuaca como Patrimonio",
                "La Quebrada de Humahuaca fue declarada Patrimonio Cultural de la Humanidad por UNESCO en 2003.",
                "Cultural",
                -23.2050, -65.3500, // Jujuy
                LocalDate.of(2003, 7, 2),
                OrigenHecho.DINAMICO,
                null,
                LocalTime.of(15, 0)
            );

            // Persistir todos los hechos
            entityManager().persist(hecho1);
            entityManager().persist(hecho2);
            entityManager().persist(hecho3);
            entityManager().persist(hecho4);
            entityManager().persist(hecho5);
            entityManager().persist(hecho6);
            entityManager().persist(hecho7);
            entityManager().persist(hecho8);

            System.out.println("✓ Se insertaron 8 hechos de prueba exitosamente");
        });

        System.out.println("Proceso completado.");
    }
}
