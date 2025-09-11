package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.enums.OrigenHecho;
import ar.edu.utn.frba.dds.enums.Provincia;
import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.Hecho;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;

public class TestProvincia {

  @Test
  @DisplayName("se obtiene una provincia a partir de un objeto coordenada")
  public void obtengoProvincia(){

    Coordenada coordenada = new Coordenada(-68,-36);

    Assertions.assertEquals(Provincia.LA_PAMPA, coordenada.obtenerProvincia());

  }
  @Test
  @DisplayName("se obtiene provincia desconocida a partir de dos coordenas que no estan en argentina")
  public void obtengoProvinciaDesconocida(){
    Coordenada coordenada = new Coordenada(-36,-68);

    Assertions.assertEquals(Provincia.PROVINCIA_DESCONOCIDA, coordenada.obtenerProvincia());
  }

  @Test
  @DisplayName("se obtiene la provincia a partir de un hecho")
  public void obtengoProvinciaAPartirDeUnHecho(){

    Hecho hechoTest = new Hecho(

        "Incendio",
        "Fuego en el bosque",
        "Incendio Forestal",
        -34.6,
        -58.4,
        LocalDate.of(2023, 10, 5),
        OrigenHecho.ESTATICO,
        "foto.png",
        LocalTime.of(12,33,45)
    );
    Assertions.assertEquals(Provincia.BUENOS_AIRES,hechoTest.getProvincia());
  }
}
