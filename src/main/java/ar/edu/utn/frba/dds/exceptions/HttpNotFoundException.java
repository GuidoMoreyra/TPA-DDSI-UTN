package ar.edu.utn.frba.dds.exceptions;

public class HttpNotFoundException extends RuntimeException {
  public HttpNotFoundException(String mensaje) {
    super(mensaje);
  }
}
