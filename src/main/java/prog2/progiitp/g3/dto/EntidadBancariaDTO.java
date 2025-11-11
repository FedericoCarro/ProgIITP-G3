package prog2.progiitp.g3.dto;

public class EntidadBancariaDTO {

    private int id;
    private String nombre;
    private String domicilioCentral;

    public EntidadBancariaDTO() {
    }

    public EntidadBancariaDTO(int id, String nombre, String domicilioCentral) {
        this.id = id;
        this.nombre = nombre;
        this.domicilioCentral = domicilioCentral;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDomicilioCentral() {
        return domicilioCentral;
    }

    public void setDomicilioCentral(String domicilioCentral) {
        this.domicilioCentral = domicilioCentral;
    }

}
