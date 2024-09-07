package pos.logic;

import java.util.Objects;
import jakarta.xml.bind.annotation.XmlAccessType;
import  jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
public class Producto {
    @XmlID
    String Codigo;
    String Descripcion;
    String UnidadMedida;
    double PrecioUnitario;
    int Existencias;
    @XmlIDREF
    Categoria categoria;

    public Producto() {
    }

    public Producto(String Codigo, String Descripcion, String UnidadMedida, double PrecioUnitario, int Existencias, Categoria categoria) {
        this.Codigo = Codigo;
        this.Descripcion = Descripcion;
        this.UnidadMedida = UnidadMedida;
        this.PrecioUnitario = PrecioUnitario;
        this.Existencias = Existencias;
        this.categoria = categoria;
    }
    @Override
    public int hashCode() {return Objects.hashCode(Codigo);}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final Producto other = (Producto) obj;
        if (!Objects.equals(this.Codigo, other.Codigo)) {return false;}
        //return Objects.equals(this.Descripcion, other.Descripcion);
        return Objects.equals(this.Codigo, other.Codigo);
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String Codigo) {
        this.Codigo = Codigo;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getUnidadMedida() {
        return UnidadMedida;
    }

    public void setUnidadMedida(String UnidadMedida) {
        this.UnidadMedida = UnidadMedida;
    }

    public double getPrecioUnitario() {
        return PrecioUnitario;
    }

    public void setPrecioUnitario(double PrecioUnitario) {
        this.PrecioUnitario = PrecioUnitario;
    }

    public int getExistencias() {
        return Existencias;
    }

    public void setExistencias(int Existencias) {
        this.Existencias = Existencias;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return Codigo;
    }
}
