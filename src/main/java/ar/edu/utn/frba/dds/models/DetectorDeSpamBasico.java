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
  private static final double UMBRAL_SIMILITUD = 0.05;

  @Override
  public boolean esSpam(String texto) {
    if (texto == null) {
      return true;
    }

    String limpio = texto.trim();
    int length = limpio.length();

    // corto -> pinta spam
    if (length < 50) {
      return true;
    }

    List<String> tokens = tokenizar(limpio);
    if (tokens.isEmpty()) {
      return true;
    }

    // repetición excesiva de palabras
    if (tieneRepeticiones(tokens)) {
      return true;
    }

    // comparación TF-IDF con corpus
    List<String> corpus = corpus();
    Map<String, Double> vectorTexto = calcularTfIdf(limpio, corpus);

    double promedio = 0.0;
    int count = 0;

    for (String doc : corpus) {
      Map<String, Double> vectorDoc = calcularTfIdf(doc, corpus);
      double sim = similitudCoseno(vectorTexto, vectorDoc);
      promedio += sim;
      count++;
    }

    if (count > 0) {
      promedio /= count;
    }

    // si el texto es MUY distinto del corpus "válido", lo marcamos como spam
    return promedio < UMBRAL_SIMILITUD;
  }

  private boolean tieneRepeticiones(List<String> tokens) {
    Map<String, Integer> contador = new HashMap<>();

    for (String t : tokens) {
      contador.put(t, contador.getOrDefault(t, 0) + 1);
    }

    // Si una palabra aparece más del 10% del total → spam
    int limite = Math.max(3, tokens.size() / 10);

    for (Integer rep : contador.values()) {
      if (rep > limite) {
        return true;
      }
    }

    return false;
  }

  /**
   * Devuelve un listado de textos aprobados que se utilizarán como corpus para el detector de spam
   */
  private List<String> corpus() {
    SolicitudesEliminacionRepository repositorioDeSolicitudes =
        SolicitudesEliminacionRepository.getInstance();

    List<SolicitudEliminacion> solicitudesRechazadas =
        repositorioDeSolicitudes.obtenerSolicitudesConEstado(EstadoSolicitudEliminacion.APROBADO);

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
        "El hecho está duplicado en otra entrada.",
        "Este hecho contiene información completamente incorrecta y desactualizada que debe ser"
            + " removida urgentemente de la base de datos. Los datos presentados no corresponden"
            + " con la realidad y podrían confundir a los usuarios que confían en esta plataforma"
            + " para obtener información precisa y verificada.",
        "La información reportada en este hecho es engañosa y no refleja la verdadera situación. Es"
            + " fundamental mantener la integridad de nuestra base de datos eliminando este tipo de"
            + " contenido que no cumple con los estándares de calidad requeridos por el sistema.",
        "Solicito la eliminación de este registro porque contiene datos erróneos que podrían"
            + " generar confusión entre los usuarios. La información debe ser precisa y confiable,"
            + " y este hecho no cumple con esos requisitos básicos de calidad.",
        "El hecho reportado no corresponde a la realidad y debe ser eliminado para preservar la"
            + " integridad del sistema. La información presentada es incorrecta y no debe"
            + " permanecer en la base de datos.",
        "Este registro fue creado por error y contiene información que no es verídica. Es"
            + " importante eliminarlo para mantener la calidad y confiabilidad de los datos en la"
            + " plataforma.",
        "La decisión de eliminar este contenido se fundamenta en consideraciones éticas y de"
            + " responsabilidad editorial. El tratamiento dado a la información resulta insensible"
            + " hacia las personas involucradas y puede contribuir a la revictimización de los"
            + " afectados. Se prioriza el respeto y la dignidad humana por encima del impacto"
            + " mediático.",
        "Solicito la remoción de este hecho porque el enfoque periodístico adoptado carece de la"
            + " sensibilidad necesaria para abordar temas delicados. La forma en que se presentó el"
            + " contenido potencia el morbo sin aportar valor informativo real, lo cual contradice"
            + " los principios éticos de esta plataforma.",
        "Este registro debe ser eliminado dado que su publicación compromete la privacidad y"
            + " dignidad de las personas mencionadas. El contenido expone detalles innecesarios que"
            + " no contribuyen al debate público y pueden generar daño colateral a individuos que"
            + " no son figuras públicas.",
        "La eliminación se solicita porque el hecho periodístico carece del contexto adecuado y"
            + " presenta la información de manera sesgada. Una cobertura responsable requiere"
            + " equilibrio, verificación exhaustiva y consideración del impacto social, elementos"
            + " que este contenido no cumple satisfactoriamente.");
  }

  /**
   * Calcula el TF-IDF de un texto dado un corpus. El TF-IDF es una medida que evalúa la importancia
   * de una palabra en un documento en relación con un corpus
   */
  private Map<String, Double> calcularTfIdf(String texto, List<String> corpus) {
    Map<String, Double> tf = calcularTf(texto);
    Map<String, Double> tfidf = new HashMap<>();

    for (Map.Entry<String, Double> entrada : tf.entrySet()) {
      String palabra = entrada.getKey();
      double tfValor = entrada.getValue();

      int frecuenciaEnDocumento =
          (int) corpus.stream().filter(documento -> tokenizar(documento).contains(palabra)).count();
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
      tf.put(palabra, tf.getOrDefault(palabra, 0.0) + 1.0);
    }

    tf.replaceAll((String k, Double v) -> v / tokens.size());

    return tf;
  }

  /**
   * Divide el texto en palabras, eliminando puntuación, palabras cortas y convirtiendo a minúsculas
   */
  private List<String> tokenizar(String texto) {
    return Arrays.stream(texto.toLowerCase().split("\\W+"))
        .filter(palabra -> palabra.length() > 2)
        .collect(Collectors.toList());
  }

  /**
   * Calcula la similitud coseno entre dos vectores TF-IDF. La similitud coseno es una medida de
   * similitud entre dos vectores que se define como el coseno del ángulo entre ellos
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

    return (mag1 == 0 || mag2 == 0) ? 0.0 : dot / (Math.sqrt(mag1) * Math.sqrt(mag2));
  }
}
