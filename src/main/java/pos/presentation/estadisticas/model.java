package pos.presentation.estadisticas;
import pos.logic.Categoria;
import pos.presentation.AbstractModel;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.beans.PropertyChangeListener;
import java.time.Month; //nuevo
import java.time.format.TextStyle; //nuevo
import java.util.ArrayList;
import java.util.List;
import java.util.Locale; //nuevo

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
        /*-----------------*/
        firePropertyChange(CATEGORIES);
        firePropertyChange(ROWS);
        firePropertyChange(COLS);
        firePropertyChange(DATA);
        /*-----------------*/
    }

    public model() {
    }

    public void init(List<Categoria> list , Rango ra){
        this.categoriasAll = list;
        this.rango = ra;
        this.categorias = new ArrayList<>();
    }

    /*Metodos get y set*/

    public List<Categoria> getCategoriasAll() {return categoriasAll;}

    public List<Categoria> getCategorias() {return categorias;}
    public Rango getRango() {return rango;}


    public void setCategoriasAll(List<Categoria> categoriasAll) {
        this.categoriasAll = categoriasAll;
        firePropertyChange(CATEGORIES_ALL);
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
        firePropertyChange(CATEGORIES);
    }

    public void setRango(Rango rango) {
        this.rango = rango;
        firePropertyChange(RANGE);
    }
    public void setRows(String[] rows) {this.rows = rows; firePropertyChange(ROWS);}
    public void setCols(String[] cols) {this.cols = cols; firePropertyChange(COLS);}
    /*--------------------*/
    public  void setData(float[][]data) {
        this.data = data;
        firePropertyChange(model.DATA);
        firePropertyChange(model.COLS);
        firePropertyChange(model.ROWS);
        firePropertyChange(model.RANGE);
    }

    public  TableModel getTableModel() {
        return new AbstractTableModel() {
            @Override
            public int getRowCount() {
                return (rows != null) ? rows.length : 0;  // Verifica si rows es null
            }

            @Override
            public int getColumnCount() {
                return (cols != null) ? cols.length + 1 : 1;  // Verifica si cols es null
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                if (rows == null || cols == null) {
                    return null;  // Maneja el caso cuando rows o cols son null
                }
                if (columnIndex == 0) {
                    return rows[rowIndex];
                } else {
                    return (data != null) ? data[rowIndex][columnIndex - 1] : null;  // Maneja el caso cuando data es null
                }
            }

            @Override
            public String getColumnName(int column) {
                if (cols == null) {
                    return (column == 0) ? "Categoria" : "";  // Maneja el caso cuando cols es null
                }
                return (column == 0) ? "Categoria" : cols[column - 1];
            }
        };
    }

    public static final String  CATEGORIES_ALL="categorias_all";
    public static final String  CATEGORIES="categorias";
    public static final String RANGE="range";
    public static final String ROWS="rows";
    public static final String COLS="cols";
    public static final String DATA="data";
}
