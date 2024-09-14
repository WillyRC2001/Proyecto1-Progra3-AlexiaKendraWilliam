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


    public void init(List<Categoria> list , Rango ra){
        this.categoriasAll = list;
        this.rango = ra;
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

    public  void setRango(Rango rango) {this.rango = rango;
        firePropertyChange(RANGE);
    }



    /*---------------*/
    /*Metodo del profe no sirve */
//    public TableModel getTableModel() {
//        return new AbstractTableModel() {
//            @Override
//            public int getRowCount() {return rows.size();}
//            public int getColumnCount() {return cols.length+1;}
//
//            @Override
//            public Object getValueAt(int rowIndex, int columnIndex) {
//                if (columnIndex == 0) {
//                    return rows.get(rowIndex);
//                }
//                else {
//                    return data[rowIndex][columnIndex]-1;
//                }
//            }
//
//            @Override
//            public String getColumnName(int column) {
//                if (column == 0) {
//                    return "Categoria";
//                }
//                else{
//                    return cols[column-1];
//                }
//            }
//        };
//    }

    public static final String  CATEGORIES_ALL="categorias_all";
    public static final String  CATEGORIES="categorias";
    public static final String RANGE="range";
    public static final String ROWS="rows";
    public static final String COLS="cols";
    public static final String DATA="data";
}
