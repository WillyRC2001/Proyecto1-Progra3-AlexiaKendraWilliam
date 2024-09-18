
package pos.presentation.cajeros;
import pos.Application;
import pos.logic.Cajero;

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


public class View   implements PropertyChangeListener{
    private JPanel panel;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JTextField nombre;
    private JButton borrarButton;
    private JTable list;
    private JTextField searchNombre;
    private JButton buscarButton;
    private JButton reporteButton;
    private JLabel idLbl;
    private JTextField id;
    private JLabel nombreLbl;
    private JLabel searchNombreLbl;


    public JPanel getPanel() {
        return panel;
    }

    public View() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Cajero filter = new Cajero();
                    filter.setNombre(searchNombre.getText());
                    controller.search(filter);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validate()) {
                    Cajero n = take();
                    try {
                        controller.save(n);
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = list.getSelectedRow();
                controller.edit(row);
            }
        });

        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.delete();
                    JOptionPane.showMessageDialog(panel, "REGISTRO BORRADO", "", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.clear();
            }
        });
        // Listener para el botón de generar PDF
        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.generatePdf(); // Llama al método para generar el PDF
            }
        });
    }

    private boolean validate() {
        boolean valid = true;
        if (id.getText().isEmpty()) {
            valid = false;
            idLbl.setBorder(Application.BORDER_ERROR);
            idLbl.setToolTipText("Codigo requerido");
        } else {
            idLbl.setBorder(null);
            idLbl.setToolTipText(null);
        }

        if (nombre.getText().isEmpty()) {
            valid = false;
            nombreLbl.setBorder(Application.BORDER_ERROR);
            nombreLbl.setToolTipText("Nombre requerido");
        } else {
            nombreLbl.setBorder(null);
            nombreLbl.setToolTipText(null);
        }
        return valid;
    }


    public Cajero take() {
        Cajero e = new Cajero();
        e.setId("CAJ-"+id.getText());
        e.setNombre(nombre.getText());
        return e;
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
            case pos.presentation.cajeros.Model.LIST:
                int[] cols = {pos.presentation.cajeros.TableModel.ID, pos.presentation.cajeros.TableModel.NOMBRE};
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                break;
            case pos.presentation.cajeros.Model.CURRENT:
                id.setText(model.getCurrent().getId());
                nombre.setText(model.getCurrent().getNombre());
                if (model.getMode() == Application.MODE_EDIT) {
                    id.setEnabled(false);
                    borrarButton.setEnabled(true);
                } else {
                    id.setEnabled(true);
                    borrarButton.setEnabled(false);
                }

                idLbl.setBorder(null);
                idLbl.setToolTipText(null);
                nombre.setBorder(null);
                nombre.setToolTipText(null);
                break;
            case pos.presentation.cajeros.Model.FILTER:
                searchNombre.setText(model.getFilter().getNombre());
                break;
        }

        this.panel.revalidate();
    }

}
