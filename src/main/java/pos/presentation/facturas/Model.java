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
    //List<Producto> productosComprados;
    List<Linea> lineaComprados;
    List<Cliente> clientes;
    List<Cajero> cajeros;
    Factura current;
    Producto filter;
    Cliente currentCli;
    Cajero currentCaj;
    int mode;

    public void setSelectedItem(Object anItem){}
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(PRODUCTOSTOTALES);
        firePropertyChange(LINEACOMPRADOS);
        firePropertyChange(CLIENTES);
        firePropertyChange(CAJEROS);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    public Model() {}

    public List<Producto> getProductosTotales () {
        return productosTotales;
    }
        public List<Linea> getLineaComprados() {
        return lineaComprados;
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
    public Producto getFilter(){
        return filter;
    }
    public int getMode() {
        return mode;
    }

    public void setProductosTotales(List<Producto> productosTotales) {
        this.productosTotales = productosTotales;
        firePropertyChange(PRODUCTOSTOTALES);
    }
    public void setProductosComprados(List<Linea> lineaComprados) {
        this.lineaComprados = lineaComprados;
        firePropertyChange(LINEACOMPRADOS);
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
    public void setFilter(Producto filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }
    public void setMode(int mode) {
        this.mode = mode;
    }

    public void init(List<Producto> listPT,List<Linea> linC, List<Cliente> listCli, List<Cajero> listCaj){
        this.productosTotales = listPT;
        this.lineaComprados = linC;
        this.clientes = listCli;
        this.cajeros = listCaj;
        this.filter = new Producto();
        this.current = new Factura();
        this.mode = Application.MODE_CREATE;

    }
    public void addLineaComprada(Linea linea) {
        if (this.lineaComprados == null) {
            this.lineaComprados = new ArrayList<>();
        }
        this.lineaComprados.add(linea);
        firePropertyChange(LINEACOMPRADOS);
    }
    public void deleteLineaComprada(int fila){
        this.lineaComprados.remove(fila);
        firePropertyChange(LINEACOMPRADOS);
    }
    public void changeCantidad(int cant){
        //this.lineaComprados.
        firePropertyChange(LINEACOMPRADOS);
    }
    public static final String PRODUCTOSTOTALES="productosTotales";
    public static final String LINEACOMPRADOS="lineaComprados";
    public static final String CLIENTES="clientes";
    public static final String CAJEROS="cajeros";
    public static final String FILTER="filter";
    public static final String CURRENT="current";
}
