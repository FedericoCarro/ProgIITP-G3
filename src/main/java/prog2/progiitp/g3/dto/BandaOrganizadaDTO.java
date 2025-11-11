package prog2.progiitp.g3.dto;

public class BandaOrganizadaDTO {
    private int id;
    private String nombre;
    private int cantMiembros;

    public BandaOrganizadaDTO() {
    }

    public BandaOrganizadaDTO(int id, String nombre, int cantMiembros) {
        this.id = id;
        this.nombre = nombre;
        this.cantMiembros = cantMiembros;
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

    public int getCantMiembros() {
        return cantMiembros;
    }

    public void setCantMiembros(int cantMiembros) {
        this.cantMiembros = cantMiembros;
    }

    
    
}