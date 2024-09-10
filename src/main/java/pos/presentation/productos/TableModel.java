package pos.presentation.productos;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import pos.Application;
import pos.logic.Producto;
import pos.logic.Service;
import pos.presentation.AbstractTableModel;
import pos.logic.Categoria;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.List;

public class TableModel extends AbstractTableModel<Producto> implements javax.swing.table.TableModel {

    public TableModel(int[] cols, List<Producto> rows) {
        super(cols, rows);
    }

    public static final int CODIGO=0;
    public static final int DESCRIPCION=1;
    public static final int UNIDAD=2;
    public static final int PRECIO=3;
    public static final int EXISTENCIA=4;
    public static final int CATEGORIA=5;

    @Override
    protected Object getPropetyAt(Producto e, int col) {
        switch (cols[col]){
            case CODIGO: return e.getCodigo();
            case DESCRIPCION: return e.getDescripcion();
            case UNIDAD:return  e.getUnidadMedida();
            case PRECIO:return  e.getPrecioUnitario();
            case EXISTENCIA:return  e.getExistencias();
            case CATEGORIA://return  e.getCategoria().getNombre();
                Categoria categoria = e.getCategoria();
                return categoria != null ? categoria.getNombre() : "";
            default: return "";
        }
    }

    public void setProductos(List<Producto> productos) {
        this.rows = productos;
        fireTableDataChanged();  // Notifica a la tabla que los datos han cambiado
    }

    @Override
    protected void initColNames(){
        colNames = new String[6];
        colNames[CODIGO]= "Codigo";
        colNames[DESCRIPCION]= "Descripcion";
        colNames[UNIDAD]= "Unidad";
        colNames[PRECIO]= "Precio";
        colNames[EXISTENCIA]= "Existencias";
        colNames[CATEGORIA]= "Categoria ";
    }



    /*
     String Codigo;
    String Descripcion;
    String UnidadMedida;
    double PrecioUnitario;
    int Existencias;
    Categoria categoria;
    * */
}
