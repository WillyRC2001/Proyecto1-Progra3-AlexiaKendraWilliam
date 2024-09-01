package pos.presentation.productos;
import pos.Application;
import pos.logic.Producto;
import pos.logic.Categoria;
import pos.presentation.AbstractModel;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
public class Model extends AbstractModel{
    private List<Categoria> categorias;
    Producto filter;
    List<Producto> list;
    Producto current;
    int mode;

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
        categorias.add(new Categoria("Dulces"));
        categorias.add(new Categoria("Aceites"));
        categorias.add(new Categoria("Agua"));
        categorias.add(new Categoria("Vinos"));
//fin
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
