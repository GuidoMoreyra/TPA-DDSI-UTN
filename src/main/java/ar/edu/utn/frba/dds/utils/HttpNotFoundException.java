package ar.edu.utn.frba.dds.utils;

public class HttpNotFoundException extends RuntimeException {
  public HttpNotFoundException(String mensaje) {
    super(mensaje);
  }
}
