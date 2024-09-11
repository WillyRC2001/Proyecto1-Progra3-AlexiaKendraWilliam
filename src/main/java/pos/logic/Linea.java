package pos.logic;

import jakarta.xml.bind.annotation.*;

import java.util.Objects;


@XmlAccessorType(XmlAccessType.FIELD)
public class Linea {
    private static int contador = 0; // Variable estática para el contador
    @XmlIDREF
    Producto producto;
    @XmlIDREF
    Factura factura;
    @XmlID
    String codigo;
    int cantidad;
    float descuento;


    public Linea(){

    }
    public Linea(Producto producto, Factura factura, int cantidad, float descuento) {
        this.producto = producto;
        this.factura = factura;
        //this.codigo = generarCodigo(); // Asigna un código único
        this.cantidad = cantidad;
        this.descuento = descuento;
    }
    public Producto getProducto() {return producto;}
    public void setProducto(Producto producto) {}
    public Factura getFactura() {
        return factura;
    }
    public void setFactura(Factura factura) {}
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

