package prog2.progiitp.g3.dto;

public class DetenidoDTO {
    private Integer id;
    private String nomApe;
    private Integer idBO;

    public DetenidoDTO() {
    }

    public DetenidoDTO(Integer id, String nomApe, Integer idBO) {
        this.id = id;
        this.nomApe = nomApe;
        this.idBO = idBO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomApe() {
        return nomApe;
    }

    public void setNomApe(String nomApe) {
        this.nomApe = nomApe;
    }

    public Integer getIdBO() {
        return idBO;
    }

    public void setIdBO(Integer idBO) {
        this.idBO = idBO;
    }

    
}