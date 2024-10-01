package pos.logic;

import java.util.Objects;

public class Categoria {

    String id;
    String Nombre;

    public Categoria() {
        this("", "");
    }

    public Categoria(String id) {
        this(id, "");
    }
    public Categoria(String id , String Nombre) {
        this.id= id; this.Nombre = Nombre;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null || getClass() != obj.getClass()) {return false;}
        Categoria other = (Categoria) obj;
        return Objects.equals(this.id, other.id);
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    @Override
    public String toString() {return  Nombre;}
}