package prog2.progiitp.g3.dto;
import java.time.LocalDate;

public class RoboDTO {
    private int id; 
    private int idDetenido;
    private int idSucursal;
    private int idJuez;
    private LocalDate fechaRobo;

    public RoboDTO(int id, int idDetenido, int idSucursal, int idJuez, LocalDate fechaRobo) {
        this.id = id;
        this.idDetenido = idDetenido;
        this.idSucursal = idSucursal;
        this.idJuez = idJuez;
        this.fechaRobo = fechaRobo;
    }

    public RoboDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdDetenido() {
        return idDetenido;
    }

    public void setIdDetenido(int idDetenido) {
        this.idDetenido = idDetenido;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public int getIdJuez() {
        return idJuez;
    }

    public void setIdJuez(int idJuez) {
        this.idJuez = idJuez;
    }

    public LocalDate getFechaRobo() {
        return fechaRobo;
    }

    public void setFechaRobo(LocalDate fechaRobo) {
        this.fechaRobo = fechaRobo;
    }

    
}