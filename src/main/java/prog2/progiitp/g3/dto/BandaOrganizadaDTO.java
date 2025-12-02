package prog2.progiitp.g3.dto;

public class BandaOrganizadaDTO {
    private Integer id;
    private String nombre;
    private Integer cantMiembros;

    public BandaOrganizadaDTO() {
    }

    public BandaOrganizadaDTO(Integer id, String nombre, Integer cantMiembros) {
        this.id = id;
        this.nombre = nombre;
        this.cantMiembros = cantMiembros;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCantMiembros() {
        return cantMiembros;
    }

    public void setCantMiembros(Integer cantMiembros) {
        this.cantMiembros = cantMiembros;
    }

    
    
}