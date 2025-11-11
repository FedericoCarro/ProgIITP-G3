package prog2.progiitp.g3.dto;

public class JuezDTO {
    private int clave;
    private String nombre;
    private int aniosDeServicio;

    public JuezDTO() {
    }

    public JuezDTO(int clave, String nombre, int aniosDeServicio) {
        this.clave = clave;
        this.nombre = nombre;
        this.aniosDeServicio = aniosDeServicio;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAniosDeServicio() {
        return aniosDeServicio;
    }

    public void setAniosDeServicio(int aniosDeServicio) {
        this.aniosDeServicio = aniosDeServicio;
    }

    
}