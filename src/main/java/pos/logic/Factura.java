package pos.logic;

import pos.data.LocalDateAdapter;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlID;
import jakarta.xml.bind.annotation.XmlIDREF;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Factura {
//    @XmlElementWrapper(name = "Lineas")
//    @XmlElement(name = "linea")
//    List<Linea> linea;
//    @XmlIDREF
//    Cliente cliente;
//    @XmlIDREF
//    Cajero cajero1;
//    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
//    LocalDate fecha;
//    @XmlID
//    String numero;
//
//
//    public Factura() {}
//
//    public Factura(List<Linea> lista_productos , Cliente cliente, Cajero cajero, LocalDate fecha, String numero) {
//        this.linea = lista_productos;
//        this.cliente = cliente;
//        this.cajero1 = cajero;
//        this.fecha = fecha;
//        this.numero = numero;
//    }
//
//    public List<Linea> getLista_productos() {return linea;}
//    public Cliente getCliente() {return cliente;}
//    public Cajero getCajero() {return cajero1;}
//    public LocalDate getFecha() {return fecha;}
//    public String getNumero() {return numero;}
//    public void setLista_productos(List<Linea> lista_producto) {linea = lista_producto;}
//    public void setCliente(Cliente cliente) {this.cliente = cliente;}
//    public void setCajero(Cajero cajero) {this.cajero1 = cajero;}
//    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
//    public void setNumero(String numero) {this.numero = numero;}
//
//
//    public double SubTotal(){
//        double subtotal = 0.0;
//        for (Linea linea : linea) {
//            subtotal += linea.Importe();
//        }
//        return subtotal;
//    }
//
//
//
//    public double Total(){
//        double subtotal = 0.0;
//        for (Linea linea : linea) {
//            subtotal += linea.Importe();
//        }
//        return subtotal - cliente.descuento;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Factura factura = (Factura) o;
//        return Objects.equals(numero, factura.numero);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(numero);
//    }
//
//    @Override
//    public String toString() {
//        return numero;
//    }
}
