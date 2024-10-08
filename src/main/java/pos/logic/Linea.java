package pos.logic;
import java.util.Objects;

public class Linea {
    Producto producto;
    Factura factura;
    String codigo;
    int cantidad;
    float descuento;


    public Linea(){
        this(new Producto(), new Factura(), "", 0, 0);
    }
    public Linea(Producto producto, Factura factura, String codigo, int cantidad, float descuento) {
        this.producto = producto;
        this.factura = factura;
        this.cantidad = cantidad;
        this.descuento = descuento;
        this.codigo = codigo;
    }
    public Producto getProducto() {return producto;}
    public void setProducto(Producto producto) {}
    public Factura getFactura() {
        return factura;
    }
    public void setFactura(Factura fac) {factura = fac;}
    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}
    public int getCantidad() {return cantidad;}
    public void setCantidad(int cantidad) {this.cantidad = cantidad;}
    public float getDescuento() {return descuento;}
    public void setDescuento(float descuento) {this.descuento = descuento;}


    public double Neto(){
        return  producto.getPrecioUnitario() ;
    }
    public double Importe(){
        return (Neto() * cantidad) - descuento *10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Linea linea = (Linea) o;
        return Objects.equals(codigo, linea.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    @Override
    public String toString() {
        return codigo;
    }
}

