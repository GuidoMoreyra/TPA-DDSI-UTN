package ar.edu.utn.frba.dds.enums;

public enum OrigenHecho {
  DINAMICO,
  ESTATICO,
  INTERMEDIO,
  AGREGADOR_DINAMICO,
  AGREGADOR_ESTATICO,
  AGREGADOR_INTERMEDIO;

  public static OrigenHecho mapearOrigenConAgregador(OrigenHecho origenHecho) {
    return switch (origenHecho) {
      case DINAMICO -> AGREGADOR_DINAMICO;
      case ESTATICO -> AGREGADOR_ESTATICO;
      case INTERMEDIO -> AGREGADOR_INTERMEDIO;
      case AGREGADOR_DINAMICO, AGREGADOR_ESTATICO, AGREGADOR_INTERMEDIO -> origenHecho;
      default -> throw new IllegalArgumentException(
          "El origen " + origenHecho + " ingresado no se puede mapear al agregador"
      );
    };
  }
}