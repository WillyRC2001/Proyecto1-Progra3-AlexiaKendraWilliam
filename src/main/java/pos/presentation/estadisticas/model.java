package pos.presentation.estadisticas;

import pos.Application;
import pos.logic.Categoria;
import pos.logic.Cliente;
import pos.presentation.AbstractModel;

import java.beans.PropertyChangeListener;
import java.util.List;

public class model extends AbstractModel {
    List<Categoria> categoriasAll;
    List<Categoria> categorias;
    Rango rango;
    String[] rows;
    String[] cols;
    float[][]data;

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CATEGORIES_ALL);
        firePropertyChange(RANGE);
    }

    public model() {
    }

    public void init(List<Categoria> list){

    }


    public static final String  CATEGORIES_ALL="categorias_all";
    public static final String  CATEGORIES="categorias";
    public static final String RANGE="range";
    public static final String ROWS="rows";
    public static final String COLS="cols";
    public static final String DATA="data";
}
