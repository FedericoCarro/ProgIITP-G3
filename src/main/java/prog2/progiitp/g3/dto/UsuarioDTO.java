package prog2.progiitp.g3.dto;

public class UsuarioDTO {
    private int id;
    private String contrasenia;
    private String nomApe;
    private int dni;
    private int tel;
    private int edad;
    private String permiso;

    public UsuarioDTO() {
    }

    public UsuarioDTO(int id, String contrasenia, String nomApe, int dni, int tel, int edad, String permiso) {
        this.id = id;
        this.contrasenia = contrasenia;
        this.nomApe = nomApe;
        this.dni = dni;
        this.tel = tel;
        this.edad = edad;
        this.permiso = permiso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    
}