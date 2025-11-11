package prog2.progiitp.g3.modelos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "jueces")
public class Juez {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clave; 
    private String nombre;
    private int aniosDeServicio;

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAniosDeServicio() {
        return aniosDeServicio;
    }

    public void setAniosDeServicio(int aniosDeServicio) {
        this.aniosDeServicio = aniosDeServicio;
    }
    

}