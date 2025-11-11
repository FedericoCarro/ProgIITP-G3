package prog2.progiitp.g3.dto;

public class SucursalBancariaDTO {
    private int id;
    private String domicilio;
    private int cantEmpleados;
    private int idEntidadBancaria;

    public SucursalBancariaDTO() {
    }

    public SucursalBancariaDTO(int id, String domicilio, int cantEmpleados, int idEntidadBancaria) {
        this.id = id;
        this.domicilio = domicilio;
        this.cantEmpleados = cantEmpleados;
        this.idEntidadBancaria = idEntidadBancaria;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public int getCantEmpleados() {
        return cantEmpleados;
    }

    public void setCantEmpleados(int cantEmpleados) {
        this.cantEmpleados = cantEmpleados;
    }

    public int getIdEntidadBancaria() {
        return idEntidadBancaria;
    }

    public void setIdEntidadBancaria(int idEntidadBancaria) {
        this.idEntidadBancaria = idEntidadBancaria;
    }

    
}