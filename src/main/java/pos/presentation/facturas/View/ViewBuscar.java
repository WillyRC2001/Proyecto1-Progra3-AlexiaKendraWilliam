package pos.presentation.facturas.View;

import pos.logic.Producto;
import pos.presentation.productos.TableModel;

import javax.swing.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewBuscar extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable list;
    private JLabel codigoJlb;
    private JTextField codigo;
    private TableModel tableModelProducto;
    private List<Producto> productosOriginales;
    private Producto productoSeleccionado;
    public ViewBuscar(List<Producto> productos) {
        this.productosOriginales = productos;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Definir el array de columnas según el orden de las columnas en TableModel
        int[] cols = new int[] {
                TableModel.CODIGO,
                TableModel.DESCRIPCION,
                TableModel.UNIDAD,
                TableModel.PRECIO,
                TableModel.EXISTENCIA,
                TableModel.CATEGORIA
        };

        // Crear el modelo de tabla con el array de columnas y la lista de productos
        tableModelProducto = new TableModel(cols, productos);
        list.setModel(tableModelProducto);

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onBuscar() {
        String codigoBuscar = codigo.getText().trim();  // Obtén el texto del campo de búsqueda
        List<Producto> productosFiltrados = productosOriginales.stream()
                .filter(p -> p.getCodigo().contains(codigoBuscar))
                .collect(Collectors.toList());  // Filtra los productos por el código

        // Actualiza el modelo de la tabla con los productos filtrados
        tableModelProducto.setProductos(productosFiltrados);
    }

    private void onOK() {
        int selectedRow = list.getSelectedRow();
        if (selectedRow != -1) {
            productoSeleccionado = tableModelProducto.getRowAt(selectedRow);
            dispose();  // Cierra el diálogo
        } else {
            JOptionPane.showMessageDialog(this, "No se ha seleccionado ningún producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public Producto getProductoSeleccionado() {
        return productoSeleccionado;
    }
}
