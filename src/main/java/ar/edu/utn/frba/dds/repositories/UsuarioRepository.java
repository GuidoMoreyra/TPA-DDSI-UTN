package ar.edu.utn.frba.dds.repositories;

import ar.edu.utn.frba.dds.models.Usuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;
import lombok.Getter;
import org.apache.commons.codec.digest.DigestUtils;


public class UsuarioRepository implements WithSimplePersistenceUnit {

    private static UsuarioRepository INSTANCE = new UsuarioRepository();

    public static UsuarioRepository getInstance() {
        return INSTANCE;
    }

    public void persistUsuario(Usuario usuario) {
        withTransaction(() -> {
            entityManager().persist(usuario);
        });
    }

    public Usuario getUsuario(String nombre, String contrasenia) {
        var resultados = entityManager()
                .createQuery("from Usuario where nombre = :nombre and hashPassorwd = :hashPassorwd"
                        , Usuario.class)
                .setParameter("nombre", nombre)
                .setParameter("hashPassorwd", DigestUtils.sha256Hex(contrasenia))
                .getResultList();

        return resultados.isEmpty() ? null : resultados.get(0);
    }
    public Usuario getUsuarioById(Long id) {
        var resultados = entityManager()
                .createQuery("from Usuario where id = :id", Usuario.class)
                .setParameter("id", id)
                .getResultList();

        return resultados.isEmpty() ? null : resultados.get(0);
    }

}