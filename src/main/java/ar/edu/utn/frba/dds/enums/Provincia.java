package ar.edu.utn.frba.dds.enums;

import ar.edu.utn.frba.dds.models.Coordenada;

public enum Provincia {
  BUENOS_AIRES(-41.0, -33.5, -63.0, -56.5),
  CABA(-34.7, -34.5, -58.6, -58.3),
  CATAMARCA(-29.3, -25.0, -68.5, -64.0),
  CHACO(-28.5, -24.0, -63.5, -58.5),
  CHUBUT(-46.0, -42.0, -71.5, -65.0),
  CORDOBA(-34.0, -29.0, -66.5, -61.5),
  CORRIENTES(-30.5, -27.0, -59.5, -55.5),
  ENTRE_RIOS(-34.5, -30.0, -60.5, -57.0),
  FORMOSA(-27.7, -22.0, -62.5, -57.5),
  JUJUY(-24.5, -21.5, -66.5, -64.0),
  LA_PAMPA(-39.0, -34.0, -68.5, -63.0),
  LA_RIOJA(-31.0, -27.0, -68.0, -65.0),
  MENDOZA(-36.0, -32.0, -70.5, -66.5),
  MISIONES(-28.2, -25.5, -56.0, -53.5),
  NEUQUEN(-40.0, -36.0, -71.5, -68.0),
  RIO_NEGRO(-42.0, -36.0, -70.5, -63.5),
  SALTA(-26.5, -21.5, -67.0, -62.0),
  SAN_JUAN(-32.5, -28.0, -69.5, -66.0),
  SAN_LUIS(-34.0, -32.0, -67.5, -65.0),
  SANTA_CRUZ(-51.5, -46.0, -73.5, -65.0),
  SANTA_FE(-34.5, -28.0, -62.5, -58.0),
  SANTIAGO_DEL_ESTERO(-30.5, -25.0, -65.0, -61.0),
  TIERRA_DEL_FUEGO(-55.2, -52.0, -68.5, -65.5),
  PROVINCIA_DESCONOCIDA(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
      Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY),
  TUCUMAN(-27.5, -26.0, -66.5, -64.5);


  private final double latMin;
  private final double latMax;
  private final double lonMin;
  private final double lonMax;

  Provincia(double latMin, double latMax, double lonMin, double lonMax) {
    this.latMin = latMin;
    this.latMax = latMax;
    this.lonMin = lonMin;
    this.lonMax = lonMax;
  }

  public boolean contiene(double lat, double lon) {
    return lat >= latMin && lat <= latMax
        && lon >= lonMin && lon <= lonMax;
  }

  public static Provincia obtenerProvinciaDesdeCoordenada(Coordenada coordenada) {
    for (Provincia p : Provincia.values()) {
      if (p != PROVINCIA_DESCONOCIDA
          && p.contiene(coordenada.getLatitud(), coordenada.getLongitud())) {
        return p;
      }
    }
    return PROVINCIA_DESCONOCIDA;
  }
}


