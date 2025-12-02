package prog2.progiitp.g3.dto;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public class ContratadoDTO {
    private Integer id;
    private boolean conArma;
    private Integer idVigilante;
    private Integer idSucBancaria;

    private Integer horasTrabajadas;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicioJornada;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFinJornada;

    public ContratadoDTO() {
    }

    public ContratadoDTO(Integer id, boolean conArma, Integer idVigilante, Integer idSucBancaria, Integer horasTrabajadas, LocalDate fechaInicioJornada, LocalDate fechaFinJornada) {
        this.id = id;
        this.conArma = conArma;
        this.idVigilante = idVigilante;
        this.idSucBancaria = idSucBancaria;
        this.horasTrabajadas = horasTrabajadas;
        this.fechaInicioJornada = fechaInicioJornada;
        this.fechaFinJornada = fechaFinJornada;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isConArma() {
        return conArma;
    }

    public void setConArma(boolean conArma) {
        this.conArma = conArma;
    }

    public Integer getIdVigilante() {
        return idVigilante;
    }

    public void setIdVigilante(Integer idVigilante) {
        this.idVigilante = idVigilante;
    }

    public Integer getIdSucBancaria() {
        return idSucBancaria;
    }

    public void setIdSucBancaria(Integer idSucBancaria) {
        this.idSucBancaria = idSucBancaria;
    }

    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public LocalDate getFechaInicioJornada() {
        return fechaInicioJornada;
    }

    public void setFechaInicioJornada(LocalDate fechaInicioJornada) {
        this.fechaInicioJornada = fechaInicioJornada;
    }

    public LocalDate getFechaFinJornada() {
        return fechaFinJornada;
    }

    public void setFechaFinJornada(LocalDate fechaFinJornada) {
        this.fechaFinJornada = fechaFinJornada;
    }

    
    
    
}