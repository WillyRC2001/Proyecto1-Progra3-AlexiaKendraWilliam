package pos.presentation.históricos;

import pos.Application;
import pos.logic.Factura;
import pos.presentation.históricos.FacturaTableModel;
import pos.presentation.históricos.LineaTableModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;



public class View   implements PropertyChangeListener  {
    private JTextField cliente;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable listado;
    private JTable lineas;
    private JLabel clienteLbl;
    private JPanel panel;

    public JPanel getPanel() {
        return panel;
    }

    public View() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Factura filter = new Factura();
                    filter.setNumero(cliente.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


    }


    // MVC
    Model model;
    Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case Model.LISTF:
                int[] colsF = {
                        FacturaTableModel.NUMERO,
                        FacturaTableModel.CLIENTE,
                        FacturaTableModel.CAJERO,
                        FacturaTableModel.FECHA,
                        FacturaTableModel.IMPORTE
                };
                listado.setModel(new FacturaTableModel(colsF, model.getList()));
                listado.setRowHeight(30);
                TableColumnModel columnModelF = listado.getColumnModel();
                columnModelF.getColumn(0).setPreferredWidth(150);
                columnModelF.getColumn(1).setPreferredWidth(150);
                columnModelF.getColumn(2).setPreferredWidth(150);
                columnModelF.getColumn(3).setPreferredWidth(150);
                break;
            case Model.LISTL:
                int[] colsL = {
                        LineaTableModel.CODIGO,
                        LineaTableModel.ARTICULO,
                        LineaTableModel.CATEGORIA,
                        LineaTableModel.CANTIDAD,
                        LineaTableModel.PRECIO,
                        LineaTableModel.DESCUENTO,
                        LineaTableModel.NETO,
                        LineaTableModel.IMPORTE
                };
                lineas.setModel(new LineaTableModel(colsL, model.getListL()));
                lineas.setRowHeight(30);
                TableColumnModel columnModelL = lineas.getColumnModel();
                columnModelL.getColumn(0).setPreferredWidth(200);
                columnModelL.getColumn(1).setPreferredWidth(200);
                columnModelL.getColumn(2).setPreferredWidth(200);
                columnModelL.getColumn(3).setPreferredWidth(200);
                columnModelL.getColumn(4).setPreferredWidth(200);
                columnModelL.getColumn(5).setPreferredWidth(200);
                columnModelL.getColumn(6).setPreferredWidth(200);
                columnModelL.getColumn(7).setPreferredWidth(200);
                break;

            case Model.FILTER:
                buscarButton.setText(model.getFilter().getNumero());
                break;
        }

        this.panel.revalidate();
    }



}
