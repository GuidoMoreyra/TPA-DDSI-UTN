package ar.edu.utn.frba.dds.models;

import kotlin.contracts.ReturnsNotNull;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    private String hashPassorwd;

    private String nombre;

    private Integer nivelDeAcceso; // 0 contribuyente , 1 administrador, etc.

    public Usuario() {

    }

    public Usuario(String nombre, String password) {
        super();
        this.nombre = nombre;
        this.hashPassorwd = DigestUtils.sha256Hex(password);
        nivelDeAcceso = 0;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getHashPassorwd() {
        return hashPassorwd;
    }

    public int getNivelDeAcceso(){
        return nivelDeAcceso;
    }

    public void setNivelDeAcceso(Integer nivelDeAcceso) {
        this.nivelDeAcceso = nivelDeAcceso;
    }

}