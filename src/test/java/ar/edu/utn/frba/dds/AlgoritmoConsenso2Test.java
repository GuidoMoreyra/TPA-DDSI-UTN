package ar.edu.utn.frba.dds;

import static org.junit.jupiter.api.Assertions.assertFalse;

import ar.edu.utn.frba.dds.contracts.Fuente;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.algoritmos.MayoriaSimple;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.List;

public class AlgoritmoConsenso2Test {

  @Test
  void estaConsensuadoDevuelveTrueSiElHechoEstaEnAlMenosLaMitadDeLasFuentes() {
    Hecho hecho = mock(Hecho.class);

    Fuente fuente1 = mock(Fuente.class);
    Fuente fuente2 = mock(Fuente.class);
    Fuente fuente3 = mock(Fuente.class);

    when(fuente1.existe(hecho)).thenReturn(true);
    when(fuente2.existe(hecho)).thenReturn(true);
    when(fuente3.existe(hecho)).thenReturn(false);

    MayoriaSimple mayoriaSimple = new MayoriaSimple(List.of(fuente1, fuente2, fuente3));

    assertTrue(mayoriaSimple.estaConsensuado(hecho, null));
  }

  @Test
  void estaConsensuadoDevuelveFalseSiElHechoEstaEnMenosDeLaMitadDeLasFuentes() {
    Hecho hecho = mock(Hecho.class);

    Fuente fuente1 = mock(Fuente.class);
    Fuente fuente2 = mock(Fuente.class);
    Fuente fuente3 = mock(Fuente.class);

    when(fuente1.existe(hecho)).thenReturn(true);
    when(fuente2.existe(hecho)).thenReturn(false);
    when(fuente3.existe(hecho)).thenReturn(false);

    MayoriaSimple mayoriaSimple = new MayoriaSimple(List.of(fuente1, fuente2, fuente3));

    assertFalse(mayoriaSimple.estaConsensuado(hecho, null));
  }

  @Test
  void estaConsensuadoDevuelveTrueSiElHechoEstaEnExactamenteLaMitadDeLasFuentes() {
    Hecho hecho = mock(Hecho.class);

    Fuente fuente1 = mock(Fuente.class);
    Fuente fuente2 = mock(Fuente.class);
    Fuente fuente3 = mock(Fuente.class);
    Fuente fuente4 = mock(Fuente.class);

    when(fuente1.existe(hecho)).thenReturn(true);
    when(fuente2.existe(hecho)).thenReturn(true);
    when(fuente3.existe(hecho)).thenReturn(false);
    when(fuente4.existe(hecho)).thenReturn(false);

    MayoriaSimple mayoriaSimple = new MayoriaSimple(List.of(fuente1, fuente2, fuente3, fuente4));

    assertTrue(mayoriaSimple.estaConsensuado(hecho, null));
  }
}
