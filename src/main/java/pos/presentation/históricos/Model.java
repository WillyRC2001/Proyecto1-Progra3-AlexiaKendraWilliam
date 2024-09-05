package pos.presentation.históricos;

import pos.Application;
import pos.logic.Factura;
import pos.logic.Linea;
import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class Model extends AbstractModel {
    List<Factura> listFacturas;
    List<Linea> listLinea;
    Factura filter;
    Factura current;
    int mode;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(LISTF);
        firePropertyChange(LISTL);
        firePropertyChange(CURRENT);
        firePropertyChange(FILTER);
    }

    public Model() {
    }

    public void init(List<Factura> list , List<Linea> lis){
        this.listFacturas = list;
        this.current = new Factura();
        this.filter = new Factura();
        this.listLinea = lis;
        this.mode= Application.MODE_CREATE;
    }

    public List<Factura> getList() {
        return listFacturas;
    }

    public void setList(List<Factura> list){
        this.listFacturas = list;
        firePropertyChange(LISTF);
    }

    public List<Linea> getListL() {
        return listLinea;
    }

    public void setListLinea(List<Linea> list){
        this.listLinea = list;
        firePropertyChange(LISTF);
    }

    public Factura getCurrent() {
        return current;
    }

    public void setCurrent(Factura current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Factura getFilter() {
        return filter;
    }

    public void setFilter(Factura filter) {
        this.filter = filter;
        firePropertyChange(FILTER);
    }

    public int getMode() {return mode;}
    public void setMode(int mode) {
        this.mode = mode;
    }


    public static final String LISTF="listF";
    public static final String LISTL="listL";
    public static final String CURRENT="current";
    public static final String FILTER="filter";

}
