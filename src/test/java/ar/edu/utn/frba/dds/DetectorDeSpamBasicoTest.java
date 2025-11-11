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
}

