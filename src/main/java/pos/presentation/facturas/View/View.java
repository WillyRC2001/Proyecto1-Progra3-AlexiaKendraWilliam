package pos.presentation.facturas.View;

import pos.logic.Cajero;
import pos.logic.Linea;
import pos.logic.Producto;
import pos.presentation.cajeros.TableModel;
import pos.presentation.facturas.Controller;
import pos.presentation.facturas.Model;
import pos.presentation.históricos.LineaTableModel;
import pos.logic.Cliente;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener{
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
    public View() {
        clienteJcb.setPreferredSize(new Dimension(200, 30));
        cajeroJcb.setPreferredSize(new Dimension(200, 30));
//        buscarButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try{
//                    Producto filter = new Producto();
//                    filter.setCodigo(producto.getText());   //PRO-001
//                    controller.search(filter);
//                }catch(Exception ex){
//                    JOptionPane.showMessageDialog(panel, ex.getMessage(),"Informacion",JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
        agregarButton.addActionListener(e -> {
            String codigo = producto.getText();
            if (controller != null) {
                controller.AgregarButtonClick(codigo,(Cliente) clienteJcb.getSelectedItem());
            }
        });
        quitarButton.addActionListener(e -> {
            int row = listaJT.getSelectedRow();
            if (controller != null) {
                controller.EliminarButtonClick(row);
            }
        });
        cantidadButton.addActionListener(e -> {
            ViewCantidad viewCantidad = new ViewCantidad();
            viewCantidad.setVisible(true);
            //Linea L = listaJT.
        });
    }
    pos.presentation.facturas.Model model;
    pos.presentation.facturas.Controller controller;
    public void setModel(pos.presentation.facturas.Model model){
        this.model = model;
        model.addPropertyChangeListener(this::propertyChange);
    }
    public void setController(pos.presentation.facturas.Controller controller){
        this.controller = controller;
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.CLIENTES:
                this.clienteJcb.setModel(new DefaultComboBoxModel((Object[]) model.getClientes().toArray()));
                break;
            case Model.CAJEROS:
                this.cajeroJcb.setModel(new DefaultComboBoxModel(this.model.getCajeros().toArray()));
                break;
            case Model.LINEACOMPRADOS:
                int[] cols = {LineaTableModel.CODIGO,LineaTableModel.ARTICULO,LineaTableModel.CATEGORIA,LineaTableModel.CANTIDAD, LineaTableModel.PRECIO, LineaTableModel.DESCUENTO, LineaTableModel.NETO, LineaTableModel.IMPORTE};
                listaJT.setModel(new LineaTableModel(cols,model.getLineaComprados()));
                listaJT.setRowHeight(30);
                TableColumnModel columnModel = listaJT.getColumnModel();
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                break;
        }
    }
}
