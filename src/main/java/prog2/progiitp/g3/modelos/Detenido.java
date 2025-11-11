package prog2.progiitp.g3.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "detenidos")
public class Detenido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nomApe;
    
    @ManyToOne
    @JoinColumn(name = "idBO", referencedColumnName = "id")
    private BandaOrganizada bandaOrganizada;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomApe() {
        return nomApe;
    }

    public void setNomApe(String nomApe) {
        this.nomApe = nomApe;
    }

    public BandaOrganizada getBandaOrganizada() {
        return bandaOrganizada;
    }

    public void setBandaOrganizada(BandaOrganizada bandaOrganizada) {
        this.bandaOrganizada = bandaOrganizada;
    }
}