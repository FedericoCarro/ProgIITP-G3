package prog2.progiitp.g3.dto;
import java.time.LocalDate;

public class ContratadoDTO {
    private int id;
    private boolean conArma;
    private int idVigilante;
    private int idSucBancaria;

    private int horasTrabajadas;
    private LocalDate fechaInicioJornada;
    private LocalDate fechaFinJornada;

    public ContratadoDTO() {
    }

    public ContratadoDTO(int id, boolean conArma, int idVigilante, int idSucBancaria, int horasTrabajadas, LocalDate fechaInicioJornada, LocalDate fechaFinJornada) {
        this.id = id;
        this.conArma = conArma;
        this.idVigilante = idVigilante;
        this.idSucBancaria = idSucBancaria;
        this.horasTrabajadas = horasTrabajadas;
        this.fechaInicioJornada = fechaInicioJornada;
        this.fechaFinJornada = fechaFinJornada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isConArma() {
        return conArma;
    }

    public void setConArma(boolean conArma) {
        this.conArma = conArma;
    }

    public int getIdVigilante() {
        return idVigilante;
    }

    public void setIdVigilante(int idVigilante) {
        this.idVigilante = idVigilante;
    }

    public int getIdSucBancaria() {
        return idSucBancaria;
    }

    public void setIdSucBancaria(int idSucBancaria) {
        this.idSucBancaria = idSucBancaria;
    }

    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
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