package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.contracts.DetectorDeSpam;
import ar.edu.utn.frba.dds.models.DetectorDeSpamBasico;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DetectorDeSpamBasicoTest {
  private DetectorDeSpam detector;

  @BeforeEach
  void setUp() {
    detector = new DetectorDeSpamBasico();
  }

  @Test
  void textoClaramenteSpamDevuelveTrue() {
    String texto = "¡Gana dinero rápido! Haz clic aquí para tu premio gratis";
    Assertions.assertTrue(detector.esSpam(texto));
  }

  @Test
  void textoNoSpamDevuelveFalse() {
    String texto = "Solicito la eliminación de mi hecho porque fue un error de carga.";
    Assertions.assertFalse(detector.esSpam(texto));
  }

  @Test
  void textoVacioDevuelveTrue() {
    String texto = "";
    Assertions.assertTrue(detector.esSpam(texto));
  }

  @Test
  void textoMuyCortoDevuelveTrue() {
    String texto = "ok";
    Assertions.assertTrue(detector.esSpam(texto));
  }

  @Test
  void umbralPersonalizadoSePuedeCambiar() {
    DetectorDeSpamBasico menosEstricto = new DetectorDeSpamBasico(0.01);
    DetectorDeSpamBasico muyEstricto = new DetectorDeSpamBasico(0.5);
    String borderline =
        "Este es un mensaje lo suficientemente largo pero poco relacionado con el corpus por defecto";

    // con umbral alto es spam, con umbral bajo no necesariamente
    Assertions.assertTrue(muyEstricto.esSpam(borderline));
    Assertions.assertFalse(menosEstricto.esSpam(borderline));
  }

  @Test
  void cambiarUmbralPorDefectoAfectaNuevasInstancias() {
    DetectorDeSpamBasico.setDefaultUmbralSimilitud(0.01);
    DetectorDeSpamBasico otro = new DetectorDeSpamBasico();
    Assertions.assertEquals(0.01, otro.getUmbralSimilitud());
  }
}
