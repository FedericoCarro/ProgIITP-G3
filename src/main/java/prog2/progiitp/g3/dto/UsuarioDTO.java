package prog2.progiitp.g3.dto;

public class UsuarioDTO {
    private Integer id;
    private String contrasenia;
    private String nomApe;
    private Integer dni;
    private Integer tel;
    private Integer edad;
    private String permiso;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Integer id, String contrasenia, String nomApe, Integer dni, Integer tel, Integer edad, String permiso) {
        this.id = id;
        this.contrasenia = contrasenia;
        this.nomApe = nomApe;
        this.dni = dni;
        this.tel = tel;
        this.edad = edad;
        this.permiso = permiso;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getNomApe() {
        return nomApe;
    }

    public void setNomApe(String nomApe) {
        this.nomApe = nomApe;
    }

    public Integer getDni() {
        return dni;
    }

    public void setDni(Integer dni) {
        this.dni = dni;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    
}