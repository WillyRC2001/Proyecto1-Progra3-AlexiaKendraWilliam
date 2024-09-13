package pos.presentation.productos;

import pos.Application;
import pos.logic.Categoria;
import pos.logic.Cliente;
import pos.logic.Producto;

import java.util.List;
import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View  implements PropertyChangeListener{
    private JTextField codigo;
    private JTextField descripcion;
    private JTextField unidad;
    private JTextField precio;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton borrarButton;
    private JLabel codigoLbl;
    private JLabel descripcionLbl;
    private JLabel unidadLbl;
    private JLabel precioLbl;
    private JLabel categoriaLbl;
    private JComboBox<Categoria> categoriaJcb;
    private JLabel nombreLbl;
    private JTextField nombre;
    private JButton buscarButton;
    private JButton reporteButton;
    private JTable list;
    private JPanel panel;
    private JTextField existencia;
    private JLabel existenciaLbl;

    public JPanel getPanel() {
        return panel;
    }

    public View() {

        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Producto filter = new Producto();
                    filter.setCodigo(nombre.getText());
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
                    Producto n = take();
                    try {
                        controller.save(n);
                        JOptionPane.showMessageDialog(panel, "REGISTRO APLICADO", "", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.generatePDF();
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
    }
//    private void setUpComboBox() {
//        if (model != null) {
//            //DefaultComboBoxModel<Categoria> comboBoxModel = model.getModeModel();
//            //categoriaJcb.setModel(comboBoxModel);
//            categoriaJcb.setModel(new DefaultComboBoxModel(model.getCategorias().toArray()));
//        }
//    }

    private boolean validate() {
        boolean valid = true;


        if (codigo.getText().isEmpty()) {
            valid = false;
            codigoLbl.setBorder(Application.BORDER_ERROR);
            codigoLbl.setToolTipText("Código requerido");
        } else {
            codigoLbl.setBorder(null);
            codigoLbl.setToolTipText(null);
        }

        if (descripcion.getText().isEmpty()) {
            valid = false;
            descripcionLbl.setBorder(Application.BORDER_ERROR);
            descripcionLbl.setToolTipText("Descripción requerida");
        } else {
            descripcionLbl.setBorder(null);
            descripcionLbl.setToolTipText(null);
        }


        if (unidad.getText().isEmpty()) {
            valid = false;
            unidadLbl.setBorder(Application.BORDER_ERROR);
            unidadLbl.setToolTipText("Unidad de medida requerida");
        } else {
            unidadLbl.setBorder(null);
            unidadLbl.setToolTipText(null);
        }

        try {
            int exist = Integer.parseInt(existencia.getText());
            if (exist<= 0) {
                valid = false;
                existenciaLbl.setBorder(Application.BORDER_ERROR);
                existenciaLbl.setToolTipText("El precio unitario debe ser mayor que 0");
            } else {
                existenciaLbl.setBorder(null);
                existenciaLbl.setToolTipText(null);
            }

        } catch (Exception e) {
            valid = false;
            existenciaLbl.setBorder(Application.BORDER_ERROR);
            existenciaLbl.setToolTipText("Existencia requerida");
        }

        try {
            double precioUnitario = Double.parseDouble(precio.getText());
            if (precioUnitario <= 0) {
                valid = false;
                precioLbl.setBorder(Application.BORDER_ERROR);
                precioLbl.setToolTipText("El precio unitario debe ser mayor que 0");
            } else {
                precioLbl.setBorder(null);
                precioLbl.setToolTipText(null);
            }

        } catch (NumberFormatException e) {

            valid = false;
            precioLbl.setBorder(Application.BORDER_ERROR);
            precioLbl.setToolTipText("Debe ingresar un número válido para el precio unitario");
        }


        if (categoriaJcb.getSelectedItem() == null ) {
            valid = false;
            categoriaLbl.setBorder(Application.BORDER_ERROR);
            categoriaLbl.setToolTipText("Categoría requerida");
        } else {
            categoriaLbl.setBorder(null);
            categoriaLbl.setToolTipText(null);
        }

        return valid;
    }

    public Producto take() {
        Producto e = new Producto();
        e.setCodigo(codigo.getText());
        e.setDescripcion(descripcion.getText());
        e.setUnidadMedida(unidad.getText());
        int exit = Integer.parseInt(existencia.getText());
        e.setExistencias(exit);
        double precioUnita = Double.parseDouble(precio.getText());
        e.setPrecioUnitario(precioUnita);
        Categoria categoria = (Categoria) categoriaJcb.getSelectedItem();
        e.setCategoria(categoria);
        return e;
    }



    // MVC
    pos.presentation.productos.Model model;
    pos.presentation.productos.Controller controller;

    public void setModel(Model model) {
        this.model = model;
        model.addPropertyChangeListener(this);
        //setUpComboBox();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case pos.presentation.productos.Model.LIST:
                int[] cols = {pos.presentation.productos.TableModel.CODIGO, pos.presentation.productos.TableModel.DESCRIPCION,
                        pos.presentation.productos.TableModel.UNIDAD, pos.presentation.productos.TableModel.PRECIO,
                        TableModel.EXISTENCIA, pos.presentation.productos.TableModel.CATEGORIA
                        //
                };
                list.setModel(new TableModel(cols, model.getList()));
                list.setRowHeight(30);
                TableColumnModel columnModel = list.getColumnModel();
                columnModel.getColumn(0).setPreferredWidth(150);
                columnModel.getColumn(1).setPreferredWidth(150);
                columnModel.getColumn(2).setPreferredWidth(150);
                columnModel.getColumn(3).setPreferredWidth(150);
                columnModel.getColumn(4).setPreferredWidth(150);
                columnModel.getColumn(5).setPreferredWidth(150);
                break;
            case pos.presentation.clientes.Model.CURRENT:
                codigo.setText(model.getCurrent().getCodigo());
                descripcion.setText(model.getCurrent().getDescripcion());
                unidad.setText(model.getCurrent().getUnidadMedida());
                precio.setText("" + model.getCurrent().getPrecioUnitario());
                existencia.setText( "" + model.getCurrent().getExistencias());
                //Producto current = model.getCurrent();
                //Categoria selectedCategoria = current.getCategoria();
                this.categoriaJcb.setSelectedItem(this.model.getCurrent().getCategoria());
//                if (selectedCategoria != null) {
//                    categoriaJcb.setSelectedItem(selectedCategoria);
//                }

                if (model.getMode() == Application.MODE_EDIT) {
                    codigo.setEnabled(false);
                    borrarButton.setEnabled(true);
                } else {
                    codigo.setEnabled(true);
                    borrarButton.setEnabled(false);
                }

                codigoLbl.setBorder(null);
                codigoLbl.setToolTipText(null);
                descripcionLbl.setBorder(null);
                descripcionLbl.setToolTipText(null);
                unidadLbl.setBorder(null);
                unidadLbl.setToolTipText(null);
                precioLbl.setBorder(null);
                existenciaLbl.setBorder(null);
                existenciaLbl.setToolTipText(null);
                categoriaLbl.setBorder(null);
                categoriaLbl.setToolTipText(null);
                break;
            case Model.FILTER:
                codigo.setText(model.getFilter().getCodigo());
                break;
            case Model.CATEGORIAS:
                this.categoriaJcb.setModel(new DefaultComboBoxModel(this.model.getCategorias().toArray()));
                //categoriaJcb.setModel(new DefaultComboBoxModel<>(model.getCategorias().toArray(new Categoria[0])));
                break;
        }

        this.panel.revalidate();
    }

}
