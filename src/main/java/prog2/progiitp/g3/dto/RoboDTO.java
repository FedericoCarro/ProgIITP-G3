package prog2.progiitp.g3.dto;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class RoboDTO {
    private Integer id; 
    private Integer idDetenido;
    private Integer idSucursal;
    private Integer idJuez;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaRobo;

    public RoboDTO(Integer id, Integer idDetenido, Integer idSucursal, Integer idJuez, LocalDate fechaRobo) {
        this.id = id;
        this.idDetenido = idDetenido;
        this.idSucursal = idSucursal;
        this.idJuez = idJuez;
        this.fechaRobo = fechaRobo;
    }

    public RoboDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdDetenido() {
        return idDetenido;
    }

    public void setIdDetenido(Integer idDetenido) {
        this.idDetenido = idDetenido;
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(Integer idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Integer getIdJuez() {
        return idJuez;
    }

    public void setIdJuez(Integer idJuez) {
        this.idJuez = idJuez;
    }

    public LocalDate getFechaRobo() {
        return fechaRobo;
    }

    public void setFechaRobo(LocalDate fechaRobo) {
        this.fechaRobo = fechaRobo;
    }

    
}