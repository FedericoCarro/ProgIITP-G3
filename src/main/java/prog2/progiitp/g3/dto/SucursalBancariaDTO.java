package prog2.progiitp.g3.dto;

public class SucursalBancariaDTO {
    private Integer id;
    private String domicilio;
    private Integer cantEmpleados;
    private Integer idEntidadBancaria;

    public SucursalBancariaDTO() {
    }

    public SucursalBancariaDTO(Integer id, String domicilio, Integer cantEmpleados, Integer idEntidadBancaria) {
        this.id = id;
        this.domicilio = domicilio;
        this.cantEmpleados = cantEmpleados;
        this.idEntidadBancaria = idEntidadBancaria;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getCantEmpleados() {
        return cantEmpleados;
    }

    public void setCantEmpleados(Integer cantEmpleados) {
        this.cantEmpleados = cantEmpleados;
    }

    public Integer getIdEntidadBancaria() {
        return idEntidadBancaria;
    }

    public void setIdEntidadBancaria(Integer idEntidadBancaria) {
        this.idEntidadBancaria = idEntidadBancaria;
    }

    
}