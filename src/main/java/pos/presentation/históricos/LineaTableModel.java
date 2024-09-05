package pos.presentation.históricos;

import pos.logic.Linea;
import pos.presentation.AbstractTableModel;

import java.util.List;

public class LineaTableModel extends AbstractTableModel<Linea> implements javax.swing.table.TableModel {

    public LineaTableModel(int[] cols, List<Linea> rows) {super(cols, rows);}

    public  static final int CODIGO = 0;
    public static final int ARTICULO = 1;
    public  static final int CATEGORIA = 2;
    public  static final int CANTIDAD = 3;
    public static final int PRECIO = 4;
    public static final int DESCUENTO = 5;
    public static final int NETO = 6;
    public static final int IMPORTE = 7;


    @Override
    protected Object getPropetyAt(Linea e, int col) {
        switch (cols[col]) {
            case CODIGO:
                return e.getCodigo();
            case ARTICULO:
                return e.getProducto().getCodigo();
            case CATEGORIA:
                return e.getProducto().getCategoria().getNombre();
            case CANTIDAD:
                return e.getCantidad();
            case PRECIO:
                return e.getProducto().getPrecioUnitario();
            case DESCUENTO:
                return e.getDescuento();
            case NETO:
                return e.Neto();
            case IMPORTE:
                return e.Importe();
            default:
                return "";
        }
    }



    @Override
    protected void initColNames() {
        colNames = new String[8];
        colNames[CODIGO] = "Codigo";
        colNames[ARTICULO] = "Articulo";
        colNames[CATEGORIA] = "Categoria";
        colNames[CANTIDAD] = "Cantidad";
        colNames[PRECIO] = "Precio";
        colNames[DESCUENTO] = "Descuento";
        colNames[NETO] = "Neto";
        colNames[IMPORTE] = "Importe";
    }


}
