package ar.edu.utn.frba.dds.hecho.DTO;

public class HechoLugarDTO {
    public Double latitud;
    public Double longitud;

    public HechoLugarDTO(
            Double latitud,
            Double longitud
    ) {
        this.latitud = latitud;
        this.longitud = longitud;
    }
}
