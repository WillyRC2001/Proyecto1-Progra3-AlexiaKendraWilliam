package pos.presentation.estadisticas;
import pos.logic.Categoria;
import pos.presentation.AbstractModel;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.beans.PropertyChangeListener;
//==================================================================================================================
import java.time.Month; //nuevo
import java.time.format.TextStyle; //nuevo
//==================================================================================================================
import java.util.ArrayList; //esto ya estaba
import java.util.List; //esto ya estaba
//==================================================================================================================
import java.util.Locale; //nuevo
//==================================================================================================================

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

    public  void setRango(Rango rango) {
        this.rango = rango;
        firePropertyChange(RANGE);
    }
    /*--------------------*/
    public  void setData(float[][]data) {
        this.data = data;
        firePropertyChange(model.DATA);
        firePropertyChange(model.COLS);
    }
    /*--------------------*/
    /*---------------*/
//    /*Metodo del profe no sirve */
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

    //==================================================================================================================
    //metodo de chat
    public void actualizarData() {
        Rango r = this.getRango();
        int colCount = (r.getAnoHasta() - r.getAnoDesde()) * 12 + r.getMesHasta() - r.getMesDesde() + 1;
        int rowCount = this.getCategorias().size();
        String[] cols = new String[colCount];

        for (int i = 0; i < colCount; i++) {
            int currentYear = r.getAnoDesde() + (r.getMesDesde() + i - 1) / 12;
            int currentMonth = (r.getMesDesde() + i - 1) % 12 + 1;
            String monthName = Month.of(currentMonth).getDisplayName(TextStyle.FULL, Locale.getDefault());
            cols[i] = monthName + " " + currentYear;
        }

        this.cols = cols;

        float[][] data = new float[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i][j] = (float) (Math.random() * 1000); // Generar datos ficticios
            }
        }

        this.data = data;
        firePropertyChange(model.DATA);
        firePropertyChange(model.COLS);
    }
    //==================================================================================================================
    public static final String  CATEGORIES_ALL="categorias_all";
    public static final String  CATEGORIES="categorias";
    public static final String RANGE="range";
    public static final String ROWS="rows";
    public static final String COLS="cols";
    public static final String DATA="data";
}
