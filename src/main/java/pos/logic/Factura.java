package pos.logic;

import jakarta.xml.bind.annotation.*;
import pos.data.LocalDateAdapter;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Factura {
    @XmlIDREF
    @XmlElementWrapper(name = "FLineas")
    @XmlElement(name = "Flinea")
    List<Linea> linea;
    @XmlIDREF
    Cliente cliente;
    @XmlIDREF
    Cajero cajero;
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    LocalDate fecha;
    @XmlID
    String numero;


    public Factura() {}

    public Factura(List<Linea> lista_productos , Cliente cliente, Cajero cajero, LocalDate fecha, String numero) {
        this.linea = lista_productos;
        this.cliente = cliente;
        this.cajero = cajero;
        this.fecha = fecha;
        this.numero = numero;
    }

    public List<Linea> getLista_productos() {return linea;}
    public Cliente getCliente() {return cliente;}
    public Cajero getCajero() {return cajero;}
    public LocalDate getFecha() {return fecha;}
    public String getNumero() {return numero;}
    public void setLista_productos(List<Linea> lista_producto) {linea = lista_producto;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}
    public void setCajero(Cajero cajero) {this.cajero = cajero;}
    public void setFecha(LocalDate fecha) {this.fecha = fecha;}
    public void setNumero(String numero) {this.numero = numero;}


    public double SubTotal(){
        double subtotal = 0.0;
        for (Linea linea : linea) {
            subtotal += linea.Importe();
        }
        return subtotal;
    }



    public double Total(){
        double subtotal = 0.0;
        for (Linea linea : linea) {
            subtotal += linea.Importe();
        }
        return subtotal - cliente.descuento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return Objects.equals(numero, factura.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return numero;
    }
}
