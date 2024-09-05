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
    DefaultComboBoxModel<Categoria> modeModel;

    public void setSelectedItem(Object anItem){}

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LIST);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    public Model() {
        //Prueba
        categorias = new ArrayList<>();
        categorias.add(new Categoria("CAT-002" ,"Dulces"));
        categorias.add(new Categoria("CAT-003" ,"Aceites"));
        categorias.add(new Categoria("CAT-001" ,"Agua"));
        categorias.add(new Categoria("CAT-004" , "Vinos"));
        modeModel = new DefaultComboBoxModel<>(categorias.toArray(new Categoria[0]));
//fin
    }
    public DefaultComboBoxModel<Categoria> getModeModel() {
        return modeModel;
    }

    public void setSelectedItem(Categoria categoria) {
        modeModel.setSelectedItem(categoria);
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }
    public void init(List< Producto> list){
        this.list = list;
        this.current = new  Producto();
        this.filter = new  Producto();
        this.mode= Application.MODE_CREATE;
    }

    public List<Producto> getList() {
        return list;
    }

    public void setList(List<Producto> list){
        this.list = list;
        firePropertyChange(LIST);
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

}
