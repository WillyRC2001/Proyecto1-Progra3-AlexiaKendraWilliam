package pos.presentation.facturas.View;


import pos.presentation.facturas.Model;
import pos.presentation.históricos.LineaTableModel;
import pos.logic.Cliente;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JPanel panel;
    private JTextField producto;
    private JButton agregarButton;
    private JTable listaJT;
    private JComboBox clienteJcb;
    private JComboBox cajeroJcb;
    private JButton CobrarButton;
    private JButton buscarButton;
    private JButton cantidadButton;
    private JButton quitarButton;
    private JButton descuentoButton;
    private JButton cancelarButton;
    private JLabel clienteLbl;
    private JLabel cajeroLbl;
    private JLabel productoLbl;

    public JPanel getPanel() {
        return panel;
    }

    public JTable getListaJT() {
        return listaJT;
    }

    public JComboBox<Cliente> getClienteJcb() {  // Añadido
        return clienteJcb;
    }

    public View() {
        clienteJcb.setPreferredSize(new Dimension(200, 30));
        cajeroJcb.setPreferredSize(new Dimension(200, 30));
        // Ajustar el tamaño de la JTable
        listaJT.setPreferredSize(new Dimension(800, 300));  // Ajusta las dimensiones según tus necesidades


        CobrarButton.addActionListener(e -> {
            if (controller != null) {
                controller.Cobrar();
            }
        });

        buscarButton.addActionListener(e -> {
            if (controller != null) {
                controller.BuscarProducto();
            }
        });

        agregarButton.addActionListener(e -> {
            String codigo = producto.getText();
            if (controller != null) {
                controller.AgregarButtonClick(codigo, (Cliente) clienteJcb.getSelectedItem());
            }
        });
        quitarButton.addActionListener(e -> {
            int row = listaJT.getSelectedRow();
            if (controller != null) {
                controller.EliminarButtonClick(row);
            }
        });
        cantidadButton.addActionListener(e -> {
            if (controller != null) {
                controller.CambiarCantidad();
            }
        });
    }

    pos.presentation.facturas.Model model;
    pos.presentation.facturas.Controller controller;

    public void setModel(pos.presentation.facturas.Model model) {
        this.model = model;
        model.addPropertyChangeListener(this::propertyChange);
    }

    public void setController(pos.presentation.facturas.Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.CLIENTES:
                this.clienteJcb.setModel(new DefaultComboBoxModel((Object[]) model.getClientes().toArray()));
                break;
            case Model.CAJEROS:
                this.cajeroJcb.setModel(new DefaultComboBoxModel(this.model.getCajeros().toArray()));
                break;
            case Model.LINEACOMPRADOS:
                int[] cols = {LineaTableModel.CODIGO, LineaTableModel.ARTICULO, LineaTableModel.CATEGORIA,
                        LineaTableModel.CANTIDAD, LineaTableModel.PRECIO, LineaTableModel.DESCUENTO,
                        LineaTableModel.NETO, LineaTableModel.IMPORTE};

                listaJT.setModel(new LineaTableModel(cols, model.getLineaComprados()) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;  // Deshabilitar la edición de celdas
                    }
                });

                listaJT.setRowHeight(50); // Ajustar la altura de las filas

                // Ajuste del ancho de las columnas basado en el contenido
                ajustarAnchoDeColumnas(listaJT);
                break;
        }
    }
    // Método para ajustar el ancho de las columnas
    private void ajustarAnchoDeColumnas(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 350; // Ancho mínimo
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 500) {
                width = 500;  // Ancho máximo
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}
