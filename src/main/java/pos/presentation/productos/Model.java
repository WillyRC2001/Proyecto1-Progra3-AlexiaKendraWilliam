package pos.presentation.productos;
import pos.Application;
import pos.logic.Producto;
import pos.logic.Categoria;
import pos.presentation.AbstractModel;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

public class Model extends AbstractModel{
    private
    List<Categoria> categorias;
    Producto filter;
    List<Producto> list;
    Producto current;
    int mode;

    public void setSelectedItem(Object anItem){}

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
        firePropertyChange(CATEGORIAS);
    }

    public Model() {}

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        firePropertyChange(CATEGORIAS);
    }

    public void init(){
        List<Producto> rows = new ArrayList<Producto>();
        this.setList(rows);
        this.filter = new  Producto();
        this.mode= Application.MODE_CREATE;

    }

    public List<Producto> getList() {
        return list;
    }

    public void setList(List<Producto> list){
        this.list = list;
        firePropertyChange(LIST);
        this.current = new  Producto();
    }

    public  Producto getCurrent() {
        return current;
    }

    public void setCurrent(Producto current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Producto getFilter() {
        return filter;
    }

    public void setFilter(Producto filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public static final String LIST="list";
    public static final String CURRENT="current";
    public static final String FILTER="filter";
    public static final String CATEGORIAS = "categorias";

}
