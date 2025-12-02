package prog2.progiitp.g3.dto;

public class JuezDTO {
    private Integer clave;
    private String nombre;
    private Integer aniosDeServicio;

    public JuezDTO() {
    }

    public JuezDTO(Integer clave, String nombre, Integer aniosDeServicio) {
        this.clave = clave;
        this.nombre = nombre;
        this.aniosDeServicio = aniosDeServicio;
    }

    public Integer getClave() {
        return clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAniosDeServicio() {
        return aniosDeServicio;
    }

    public void setAniosDeServicio(Integer aniosDeServicio) {
        this.aniosDeServicio = aniosDeServicio;
    }

    
}