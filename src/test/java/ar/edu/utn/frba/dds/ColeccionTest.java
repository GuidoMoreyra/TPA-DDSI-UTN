package ar.edu.utn.frba.dds;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import ar.edu.utn.frba.dds.contracts.Criterio;
import ar.edu.utn.frba.dds.models.Coleccion;
import ar.edu.utn.frba.dds.models.Coordenada;
import ar.edu.utn.frba.dds.models.Hecho;
import ar.edu.utn.frba.dds.models.criterios.CriterioCategoria;
import ar.edu.utn.frba.dds.models.criterios.CriterioFecha;
import ar.edu.utn.frba.dds.models.criterios.CriterioLugar;
import ar.edu.utn.frba.dds.contracts.Fuente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ColeccionTest {
  Fuente fuenteMock;
  Coordenada coordenadaMock;
  CriterioLugar criterioLugarMock;
  CriterioCategoria criterioCategoriaMock;
  CriterioFecha criterioFechaMock;


  @BeforeEach
  public void setup() {
    /// Seteo de mocks
    //Fuente
    fuenteMock = mock(Fuente.class);
    // Criterios de creacion de una coleccion
    criterioLugarMock = mock(CriterioLugar.class);
    criterioCategoriaMock = mock(CriterioCategoria.class);
    criterioFechaMock = mock(CriterioFecha.class);
    // Otros - por el momento sin uso, falta implementar API
    coordenadaMock = mock(Coordenada.class);
  }

  @Test
  public void siUnHechoCumpleElCriterioDeLocalidadSeDevuelveTrue() {
    // Hecho mockeados
    Hecho hecho1 = mock(Hecho.class);

    when(criterioLugarMock.cumple(hecho1)).thenReturn(true);

    // Colección con criterios de creación (fecha, lugar, categoría)
    LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
    LocalDate fechaFin = LocalDate.of(2024, 12, 31);
    Coleccion coleccion = new Coleccion(fuenteMock, "Buenos Aires", fechaInicio, fechaFin, "Educación",null);

    Boolean cumple = coleccion.cumpleCriterios(hecho1, List.of(criterioLugarMock));
    assertTrue(cumple);
  }

  @Test
  public void coleccionDevuelveHechosQueCumplenConCriteriosDeCreacion() {
    Hecho hechoMock = mock(Hecho.class);
    Coordenada coordenadaMock = mock(Coordenada.class);

    when(coordenadaMock.getLocalidad()).thenReturn("Buenos Aires");
    when(hechoMock.getCoordenadas()).thenReturn(coordenadaMock);
    when(hechoMock.estaActivo()).thenReturn(true);

    // Cumple todos los criterios de creación
    when(hechoMock.getCoordenadas().getLocalidad()).thenReturn("Buenos Aires");
    when(hechoMock.getCategoria()).thenReturn("Educación");
    when(hechoMock.getFechaDelHecho()).thenReturn(LocalDate.of(2024, 6, 1));
    when(fuenteMock.obtenerHechos()).thenReturn(List.of(hechoMock));

    when(criterioLugarMock.cumple(hechoMock)).thenReturn(true);
    when(criterioCategoriaMock.cumple(hechoMock)).thenReturn(true);
    when(criterioFechaMock.cumple(hechoMock)).thenReturn(true);

    LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
    LocalDate fechaFin = LocalDate.of(2024, 12, 31);
    Coleccion coleccion = new Coleccion(fuenteMock, "Buenos Aires", fechaInicio, fechaFin, "Educación",null);

    Boolean esIrrestricta = false;
    List<Hecho> resultado = coleccion.obtenerColeccionCriteriosCreacional(esIrrestricta);

    assertEquals(1, resultado.size());
    assertTrue(resultado.contains(hechoMock));
  }

  @Test
  public void coleccionDevuelveHechosQueCumplenConCriteriosAdicionales() {

    // Hechos mockeados
    Hecho hecho1 = mock(Hecho.class);
    Hecho hecho2 = mock(Hecho.class);
    Hecho hecho3 = mock(Hecho.class);
    Coordenada coordenadaMock = mock(Coordenada.class);

    when(coordenadaMock.getLocalidad()).thenReturn("Buenos Aires");
    when(hecho1.getCoordenadas()).thenReturn(coordenadaMock);
    when(hecho2.getCoordenadas()).thenReturn(coordenadaMock);
    when(hecho3.getCoordenadas()).thenReturn(coordenadaMock);
    when(criterioLugarMock.getLocalidad()).thenReturn("Buenos Aires");

    when(hecho1.getCoordenadas().getLocalidad()).thenReturn("Buenos Aires");
    when(hecho2.getCoordenadas().getLocalidad()).thenReturn("Buenos Aires");
    when(hecho3.getCoordenadas().getLocalidad()).thenReturn("Buenos Aires");

    when(hecho1.getCategoria()).thenReturn("Educación");
    when(hecho2.getCategoria()).thenReturn("Educación");
    when(hecho3.getCategoria()).thenReturn("Educación");

    when(hecho1.getFechaDelHecho()).thenReturn(LocalDate.of(2024, 6, 1));
    when(hecho2.getFechaDelHecho()).thenReturn(LocalDate.of(2024, 6, 1));
    when(hecho3.getFechaDelHecho()).thenReturn(LocalDate.of(2024, 6, 1));

    when(hecho1.estaActivo()).thenReturn(true);
    when(hecho2.estaActivo()).thenReturn(true);
    when(hecho3.estaActivo()).thenReturn(true);

    // La fuente devuelve 3 hechos
    when(fuenteMock.obtenerHechos()).thenReturn(List.of(hecho1, hecho2, hecho3));

    // Colección con criterios de creación (fecha, lugar, categoría)
    LocalDate fechaInicio = LocalDate.of(2024, 1, 1);
    LocalDate fechaFin = LocalDate.of(2024, 12, 31);
    Coleccion coleccion = new Coleccion(fuenteMock, "Buenos Aires", fechaInicio, fechaFin, "Educación",null);

    // Se setea qué hechos cumplen qué criterios

    //el hecho 1 cumple los 3 criterios
    when(criterioLugarMock.cumple(hecho1)).thenReturn(true);
    when(criterioCategoriaMock.cumple(hecho1)).thenReturn(true);
    when(criterioFechaMock.cumple(hecho1)).thenReturn(true);

    //el hecho 2 no pasa Criterio Categoría
    when(criterioLugarMock.cumple(hecho2)).thenReturn(true);
    when(criterioCategoriaMock.cumple(hecho2)).thenReturn(false);  //no pasa
    when(criterioFechaMock.cumple(hecho2)).thenReturn(true);

    //el hecho 3 no pasa Criterio Fecha
    when(criterioLugarMock.cumple(hecho3)).thenReturn(true);
    when(criterioCategoriaMock.cumple(hecho3)).thenReturn(true);
    when(criterioFechaMock.cumple(hecho3)).thenReturn(false);  //no pasa

    // Ejecutamos el método a testear
    List<Criterio> criteriosAdicionales = new ArrayList<Criterio>();
    criteriosAdicionales.add(criterioLugarMock);
    criteriosAdicionales.add(criterioCategoriaMock);
    criteriosAdicionales.add(criterioFechaMock);

    Boolean esIrrestrica = false;

    List<Hecho> resultado = coleccion.obtenerColeccionConCriteriosExtra(criteriosAdicionales, esIrrestrica);

    // Solo hecho1 cumple todos los criterios
    assertEquals(1, resultado.size());
    assertTrue(resultado.contains(hecho1));
    assertFalse(resultado.contains(hecho2));//deberia ser false
    assertFalse(resultado.contains(hecho3));//deberia ser false
  }

}
