package prog2.progiitp.g3.modelos;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contratados")
public class Contratado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean conArma;


    @Embedded
    private Jornada jornada;


    @ManyToOne
    @JoinColumn(name = "idVigilante", referencedColumnName = "id")
    private Usuario vigilante;
    
    @ManyToOne
    @JoinColumn(name = "idSucBancaria", referencedColumnName = "id")
    private SucursalBancaria sucursalBancaria;

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

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Usuario getVigilante() {
        return vigilante;
    }

    public void setVigilante(Usuario vigilante) {
        this.vigilante = vigilante;
    }

    public SucursalBancaria getSucursalBancaria() {
        return sucursalBancaria;
    }

    public void setSucursalBancaria(SucursalBancaria sucursalBancaria) {
        this.sucursalBancaria = sucursalBancaria;
    }


}