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

    public Data() {
        clientes = new ArrayList<>();
        cajeros = new ArrayList<>();
        categorias = new ArrayList<>();
        productos = new ArrayList<>();
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



}
