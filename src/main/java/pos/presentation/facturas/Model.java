package pos.presentation.facturas;
import pos.Application;
import pos.logic.*;

import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
public class Model extends AbstractModel{
private
    List<Producto> productosTotales;
    List<Producto> productosComprados;
    List<Cliente> clientes;
    List<Cajero> cajeros;
    Factura current;
    Factura filter;
    int mode;

    public void setSelectedItem(Object anItem){}
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(PRODUCTOSTOTALES);
        firePropertyChange(PRODUCTOSCOMPRADOS);
        firePropertyChange(CLIENTES);
        firePropertyChange(CAJEROS);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    public Model() {}

    public List<Producto> getProductosTotales () {
        return productosTotales;
    }
        public List<Producto> getProductosComprados() {
        return productosComprados;
    }
        public List<Cliente> getClientes () {
        return clientes;
    }
        public List<Cajero> getCajeros () {
        return cajeros;
    }
    public Factura getCurrent(){
        return current;
    }
    public Factura getFilter(){
        return filter;
    }
    public int getMode() {
        return mode;
    }

    public void setProductosTotales(List<Producto> productosTotales) {
        this.productosTotales = productosTotales;
        firePropertyChange(PRODUCTOSTOTALES);
    }
    public void setProductosComprados(List<Producto> productosComprados) {
        this.productosComprados = productosComprados;
        firePropertyChange(PRODUCTOSCOMPRADOS);
    }
    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
        firePropertyChange(CLIENTES);
    }
    public void setCajeros(List<Cajero> cajeros) {
        this.cajeros = cajeros;
        firePropertyChange(CAJEROS);
    }
    public void setCurrent(Factura current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }
    public void setFilter(Factura filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }
    public void setMode(int mode) {
        this.mode = mode;
    }

    public void init(List<Producto> listPT,List<Producto> listPC, List<Cliente> listCli, List<Cajero> listCaj){
        this.productosTotales = listPT;
        this.productosComprados = listPC;
        this.clientes = listCli;
        this.cajeros = listCaj;
        this.filter = new Factura();
        this.current = new Factura();
        this.mode = Application.MODE_CREATE;

    }
    public static final String PRODUCTOSTOTALES="productosTotales";
    public static final String PRODUCTOSCOMPRADOS="productosComprados";
    public static final String CLIENTES="clientes";
    public static final String CAJEROS="cajeros";
    public static final String FILTER="filter";
    public static final String CURRENT="current";
}
