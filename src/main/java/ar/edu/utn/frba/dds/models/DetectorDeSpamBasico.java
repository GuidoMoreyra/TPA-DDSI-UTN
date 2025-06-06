package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.DetectorDeSpam;
import ar.edu.utn.frba.dds.models.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.repositories.SolicitudRepositorySingleton;

import java.util.*;
import java.util.stream.Collectors;

public class DetectorDeSpamBasico implements DetectorDeSpam {
  private static final int LIMITE_CORPUS = 50;
  private static final double UMBRAL_SIMILITUD = 0.2;

  @Override
  public boolean esSpam(String texto) {
    List<String> corpus = corpus();
    Map<String, Double> tfidfTexto = calcularTFIDF(texto, corpus);

    for (String aprobado : corpus) {
      Map<String, Double> tfidfAprobado = calcularTFIDF(aprobado, corpus);

      if (similitudCoseno(tfidfTexto, tfidfAprobado) > DetectorDeSpamBasico.UMBRAL_SIMILITUD) {
        return false;
      }
    }

    return true;
  }

  /**
   * Devuelve un listado de textos aprobados que se utilizarán como corpus para el detector de spam
   */
  private List<String> corpus() {
    SolicitudRepositorySingleton repositorioDeSolicitudes = SolicitudRepositorySingleton.getInstance();
    List<SolicitudEliminacion> solicitudesRechazadas = repositorioDeSolicitudes.obtenerSolicitudesSegunEstado(
        EstadoSolicitudEliminacion.APROBADO
    );

    return solicitudesRechazadas.isEmpty()
        ? corpusPorDefault()
        : solicitudesRechazadas.stream()
        .limit(DetectorDeSpamBasico.LIMITE_CORPUS)
        .map(SolicitudEliminacion::getJustificacion)
        .toList();
  }

  private List<String> corpusPorDefault() {
    return List.of(
        "spam",
        "oferta",
        "gratis",
        "promocion",
        "descuento",
        "ganar dinero",
        "click aqui",
        "haz clic aqui",
        "haz click aqui",
        "haz clic en este enlace"
    );
  }

  /**
   * Calcula el TF-IDF de un texto dado un corpus.
   * El TF-IDF es una medida que evalúa la importancia de una palabra
   * en un documento en relación con un corpus
   */
  private Map<String, Double> calcularTFIDF(String texto, List<String> corpus) {
    Map<String, Double> tf = calcularTF(texto);
    Map<String, Double> tfidf = new HashMap<>();

    for (String palabra : tf.keySet()) {
      int frecuenciaEnDocumento =
          (int) corpus.stream()
              .filter(documento -> tokenizar(documento).contains(palabra))
              .count();

      double idf = Math.log(
          (double) (corpus.size() + 1) / (frecuenciaEnDocumento + 1)
      );

      tfidf.put(
          palabra,
          tf.get(palabra) * idf
      );
    }

    return tfidf;
  }

  /**
   * Calcula el Term Frequency (TF) de un texto, que es la frecuencia de cada palabra en el texto
   * normalizada por el número total de palabras
   */
  private Map<String, Double> calcularTF(String texto) {
    Map<String, Double> tf = new HashMap<>();
    List<String> tokens = tokenizar(texto);

    for (String palabra : tokens) {
      tf.put(
          palabra,
          tf.getOrDefault(palabra, 0.0) + 1.0
      );
    }

    tf.replaceAll(
        (String k, Double v) -> v / tokens.size()
    );

    return tf;
  }

  /**
   * Divide el texto en palabras, eliminando puntuación, palabras cortas y convirtiendo a minúsculas.
   */
  private List<String> tokenizar(String texto) {
    return Arrays.stream(
        texto
            .toLowerCase()
            .split("\\W+")
        )
        .filter(
            palabra -> palabra.length() > 2
        )
        .collect(
            Collectors.toList()
        );
  }

  /**
   * Calcula la similitud coseno entre dos vectores TF-IDF.
   * La similitud coseno es una medida de similitud entre dos vectores
   * que se define como el coseno del ángulo entre ellos
   */
  private double similitudCoseno(Map<String, Double> vector1, Map<String, Double> vector2) {
    Set<String> todas = new HashSet<>();
    todas.addAll(vector1.keySet());
    todas.addAll(vector2.keySet());

    double dot = 0.0, mag1 = 0.0, mag2 = 0.0;

    for (String palabra : todas) {
      double a = vector1.getOrDefault(palabra, 0.0);
      double b = vector2.getOrDefault(palabra, 0.0);
      dot += a * b;
      mag1 += a * a;
      mag2 += b * b;
    }

    return (mag1 == 0 || mag2 == 0)
        ? 0.0
        : dot / (Math.sqrt(mag1) * Math.sqrt(mag2));
  }
}