package ar.edu.utn.frba.dds;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.algoritmos.ConsensoAbsoluto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

class AlgoritmoConsensoTest {

  @Test
  void estaConsensuadoDevuelveTrueSiTodasLasFuentesConfirman() {
    Hecho hecho = mock(Hecho.class);

    Fuente fuente1 = mock(Fuente.class);
    Fuente fuente2 = mock(Fuente.class);

    when(fuente1.existe(hecho)).thenReturn(true);
    when(fuente2.existe(hecho)).thenReturn(true);

    ConsensoAbsoluto consenso = new ConsensoAbsoluto(List.of(fuente1, fuente2));

    assertTrue(consenso.estaConsensuado(hecho, fuente1));
  }

  @Test
  void estaConsensuadoDevuelveFalseSiAlgunaFuenteNoLoConfirma() {
    Hecho hecho = mock(Hecho.class);

    Fuente fuente1 = mock(Fuente.class);
    Fuente fuente2 = mock(Fuente.class);

    when(fuente1.existe(hecho)).thenReturn(true);
    when(fuente2.existe(hecho)).thenReturn(false);

    ConsensoAbsoluto consenso = new ConsensoAbsoluto(List.of(fuente1, fuente2));

    assertFalse(consenso.estaConsensuado(hecho, fuente1));
  }
}
