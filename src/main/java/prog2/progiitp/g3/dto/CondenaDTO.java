package prog2.progiitp.g3.dto;
import java.time.LocalDate;

public class CondenaDTO {
    private int id;
    private int idRobo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public CondenaDTO() {
    }

    public CondenaDTO(int id, int idRobo, LocalDate fechaInicio, LocalDate fechaFin) {
        this.id = id;
        this.idRobo = idRobo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRobo() {
        return idRobo;
    }

    public void setIdRobo(int idRobo) {
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