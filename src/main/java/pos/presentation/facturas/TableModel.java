//package pos.presentation.facturas;
//import pos.logic.Cliente;
//import pos.logic.Producto;
//import pos.presentation.AbstractTableModel;
//import java.util.List;
//
//public class TableModel extends AbstractTableModel<Producto> implements javax.swing.table.TableModel {
//    public TableModel(int[] cols, List<Producto> rows) {super (cols, rows);}
//    public static final int CODIGO = 0;
//    public static final int DESCRIPCION = 1;
//    public static final int CATEGORIA = 2;
//    public static final int CANTIDAD = 3;
//    public static final int PRECIO = 4;
//    public static final int DESCUENTO = 5;
//    public static final int NETO = 6;
//    public static final int IMPORTE = 7;
//
//    @Override
//    protected Object getPropetyAt(Producto e, int col){
//        switch (cols[col]){
//            case CODIGO: return e.getCodigo();
//            case DESCRIPCION: return e.getDescripcion();
//            case CATEGORIA: return e.getCategoria();
//            case PRECIO: return e.getPrecioUnitario();
//            //case DESCUENTO: return e.getDescuento();
//            //case CANTIDAD:
//            //case NETO:    (PRECIO - DESCUENTO)
//            //case IMPORTE: (NETO * CANTIDAD)
//
//        }
//    }
//}
