package pos.presentation.facturas.View;

import pos.presentation.facturas.Controller;
import pos.presentation.facturas.Model;

import javax.swing.*;
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
    public View(){
//        buscarButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try{
//
//                }catch(Exception ex){
//                    JOptionPane.showMessageDialog(panel, ex.getMessage(),"Informacion",JOptionPane.INFORMATION_MESSAGE);
//                }
//            }
//        });
    }
    pos.presentation.facturas.Model model;
    pos.presentation.facturas.Controller controller;
    public void setModel(pos.presentation.facturas.Model model){
        this.model = model;
        model.addPropertyChangeListener(this::propertyChange);
    }
    public void setController(pos.presentation.facturas.Controller controller){
        this.controller = controller;
        //model.addPropertyChangeListener(this::propertyChange);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case Model.CLIENTES:
                this.clienteJcb.setModel(new DefaultComboBoxModel((Object[]) model.getClientes().toArray()));
                break;
            case Model.CAJEROS:
                this.cajeroJcb.setModel(new DefaultComboBoxModel(this.model.getClientes().toArray()));
                break;

        }
    }
}
