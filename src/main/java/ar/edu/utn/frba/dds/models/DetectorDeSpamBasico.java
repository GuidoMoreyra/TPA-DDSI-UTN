package ar.edu.utn.frba.dds.models;

import ar.edu.utn.frba.dds.contracts.DetectorDeSpam;
import ar.edu.utn.frba.dds.enums.EstadoSolicitudEliminacion;
import ar.edu.utn.frba.dds.repositories.SolicitudesEliminacionRepository;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class DetectorDeSpamBasico implements DetectorDeSpam {
  private static final int LIMITE_CORPUS = 50;
  private static final double UMBRAL_SIMILITUD = 0.3;

  @Override
  public boolean esSpam(String texto) {
    List<String> corpus = corpus();
    Map<String, Double> tfidfTexto = calcularTfIdf(texto, corpus);

    for (String aprobado : corpus) {
      Map<String, Double> tfidfAprobado = calcularTfIdf(aprobado, corpus);

      if (similitudCoseno(tfidfTexto, tfidfAprobado) >= DetectorDeSpamBasico.UMBRAL_SIMILITUD) {
        return false;
      }
    }

    return true;
  }

  /**
   * Devuelve un listado de textos aprobados que se utilizarán como corpus para el detector de spam
   */
  private List<String> corpus() {
    SolicitudesEliminacionRepository repositorioDeSolicitudes =
        SolicitudesEliminacionRepository.getInstance();

    List<SolicitudEliminacion> solicitudesRechazadas =
        repositorioDeSolicitudes.obtenerSolicitudesConEstado(
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
        "Solicito la eliminación de este hecho porque fue un error de carga.",
        "El hecho ya no corresponde a la situación actual.",
        "Se ingresó por equivocación y no refleja lo ocurrido.",
        "La ubicación fue mal registrada y es incorrecta.",
        "Ya no tiene validez y debe ser eliminado.",
        "Fue reportado por accidente y deseo que se retire.",
        "El contenido no representa la realidad del hecho.",
        "Pedí eliminarlo porque contiene datos incorrectos.",
        "El hecho está duplicado en otra entrada."
    );
  }

  /**
   * Calcula el TF-IDF de un texto dado un corpus.
   * El TF-IDF es una medida que evalúa la importancia de una palabra
   * en un documento en relación con un corpus
   */
  private Map<String, Double> calcularTfIdf(String texto, List<String> corpus) {
    Map<String, Double> tf = calcularTf(texto);
    Map<String, Double> tfidf = new HashMap<>();

    for (Map.Entry<String, Double> entrada : tf.entrySet()) {
      String palabra = entrada.getKey();
      double tfValor = entrada.getValue();

      int frecuenciaEnDocumento = (int) corpus.stream()
          .filter(documento -> tokenizar(documento).contains(palabra))
          .count();
      double idf = Math.log((double) (corpus.size() + 1) / (frecuenciaEnDocumento + 1));

      tfidf.put(palabra, tfValor * idf);
    }

    return tfidf;
  }

  /**
   * Calcula el Term Frequency (TF) de un texto, que es la frecuencia de cada palabra en el texto
   * normalizada por el número total de palabras
   */
  private Map<String, Double> calcularTf(String texto) {
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
   * Divide el texto en palabras, eliminando puntuación,
   * palabras cortas y convirtiendo a minúsculas
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

    double dot = 0.0;
    double mag1 = 0.0;
    double mag2 = 0.0;

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