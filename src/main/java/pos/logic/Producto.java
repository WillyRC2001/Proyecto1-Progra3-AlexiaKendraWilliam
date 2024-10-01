package pos.logic;

import java.util.Objects;

public class Producto {

    String Codigo;
    String Descripcion;
    String UnidadMedida;
    double PrecioUnitario;
    int Existencia;
    Categoria categoria;

    public Producto() {
        this("","","",0,  0, new Categoria());
    }

    public Producto(String Codigo, String Descripcion, String UnidadMedida, double PrecioUnitario, int existencia, Categoria categoria) {
        this.Codigo = Codigo;
        this.Descripcion = Descripcion;
        this.UnidadMedida = UnidadMedida;
        this.PrecioUnitario = PrecioUnitario;
        this.Existencia = existencia;
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
        return Existencia;
    }

    public void setExistencias(int Existencias) {
        this.Existencia = Existencias;
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
