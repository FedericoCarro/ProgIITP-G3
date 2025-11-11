package prog2.progiitp.g3.modelos;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "robos")
public class Robo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate fechaRobo;
    
    @ManyToOne
    @JoinColumn(name = "idDetenido", referencedColumnName = "id")
    private Detenido detenido;
    
    @ManyToOne
    @JoinColumn(name = "idSucursal", referencedColumnName = "id")
    private SucursalBancaria sucursal;
    
    @ManyToOne
    @JoinColumn(name = "idJuez", referencedColumnName = "clave")
    private Juez juez;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFechaRobo() {
        return fechaRobo;
    }

    public void setFechaRobo(LocalDate fechaRobo) {
        this.fechaRobo = fechaRobo;
    }

    public Detenido getDetenido() {
        return detenido;
    }

    public void setDetenido(Detenido detenido) {
        this.detenido = detenido;
    }

    public SucursalBancaria getSucursal() {
        return sucursal;
    }

    public void setSucursal(SucursalBancaria sucursal) {
        this.sucursal = sucursal;
    }

    public Juez getJuez() {
        return juez;
    }

    public void setJuez(Juez juez) {
        this.juez = juez;
    }


}