package prog2.progiitp.g3.dto;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class CondenaDTO {
    private Integer id;
    private Integer idRobo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    public CondenaDTO() {
    }

    public CondenaDTO(Integer id, Integer idRobo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.idRobo = idRobo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdRobo() {
        return idRobo;
    }

    public void setIdRobo(Integer idRobo) {
        this.idRobo = idRobo;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
    
}