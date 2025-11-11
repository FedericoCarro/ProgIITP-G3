package prog2.progiitp.g3.dto;

public class DetenidoDTO {
    private int id;
    private String nomApe;
    private int idBO;

    public DetenidoDTO() {
    }

    public DetenidoDTO(int id, String nomApe, int idBO) {
        this.id = id;
        this.nomApe = nomApe;
        this.idBO = idBO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomApe() {
        return nomApe;
    }

    public void setNomApe(String nomApe) {
        this.nomApe = nomApe;
    }

    public int getIdBO() {
        return idBO;
    }

    public void setIdBO(int idBO) {
        this.idBO = idBO;
    }

    
}