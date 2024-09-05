package pos.data;

import pos.logic.*;
import jakarta.xml.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data {

    @XmlElementWrapper(name = "clientes")
    @XmlElement(name = "cliente")
    private List<Cliente> clientes;
    @XmlElementWrapper(name = "cajeros")
    @XmlElement(name = "cajero")
    private List<Cajero> cajeros;
    @XmlElementWrapper(name = "productos")
    @XmlElement(name = "producto")
    private List<Producto> productos;
    @XmlElementWrapper(name = "categorias")
    @XmlElement(name = "categoria")
    private List<Categoria> categorias;
    @XmlElementWrapper(name = "lineas")
    @XmlElement(name = "linea")
    private List<Linea> lineas;
    @XmlElementWrapper(name = "facturas")
    @XmlElement(name = "factura")
    private List<Factura> facturas;


    public Data() {
        clientes = new ArrayList<>();
        cajeros = new ArrayList<>();
        categorias = new ArrayList<>();
        productos = new ArrayList<>();
        lineas = new ArrayList<>();
        facturas = new ArrayList<>();
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
    public List<Cajero> getCajeros() {
        return cajeros;
    }
    public List<Categoria> getCategorias() {
        return categorias;
    }
    public List<Producto> getProductos() {
        return productos;
    }
    public List<Linea> getLineas() {return lineas;}
    public List<Factura> getFacturas() {return facturas;}


}
