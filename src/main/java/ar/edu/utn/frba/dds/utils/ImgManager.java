package ar.edu.utn.frba.dds.utils;

import io.javalin.http.UploadedFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

public class ImgManager {

    // Carpeta donde se guardan las imágenes
    private static final String UPLOAD_DIR = "src/main/resources/public/uploads/";

    /**
     * Guarda una imagen subida por el usuario y devuelve la ruta pública (URL)
     * @param file imagen recibida desde el formulario
     * @return ruta relativa (por ejemplo: "/uploads/imagen123.jpg")
     */
    public static String Guardar(UploadedFile file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("No se ha recibido ningún archivo.");
        }

        // Crea la carpeta si no existe
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Genera un nombre único para evitar conflictos
        String extension = getFileExtension(file.filename());
        String nombreArchivo = UUID.randomUUID() + (extension.isEmpty() ? "" : "." + extension);

        // Ruta completa en disco
        File destino = new File(uploadDir, nombreArchivo);

        // Guarda el archivo
        try (FileOutputStream out = new FileOutputStream(destino)) {
            out.write(file.content().readAllBytes());
        }

        // Retorna la ruta relativa accesible desde el navegador
        return "/uploads/" + nombreArchivo;
    }

    /**
     * Dada una ruta interna, devuelve los bytes de la imagen.
     * @param rutaRelativa Ej: "/uploads/imagen123.jpg"
     * @return arreglo de bytes de la imagen
     */
    public static byte[] Obtener(String rutaRelativa) throws IOException {
        File archivo = new File("src/main/resources/public" + rutaRelativa);
        if (!archivo.exists()) {
            throw new IOException("La imagen no existe en la ruta: " + rutaRelativa);
        }
        return Files.readAllBytes(archivo.toPath());
    }

    // Utilidad para obtener la extensión del archivo
    private static String getFileExtension(String filename) {
        int index = filename.lastIndexOf('.');
        return (index > 0) ? filename.substring(index + 1) : "";
    }
}
