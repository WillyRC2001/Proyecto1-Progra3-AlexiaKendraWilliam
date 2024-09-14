package pos.presentation.estadisticas;

import pos.presentation.productos.Controller;
import pos.presentation.productos.Model;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class View implements PropertyChangeListener {
    private JComboBox añoCbx;
    private JComboBox mesCbx;
    private JComboBox añoCbx2;
    private JComboBox categoriaCbx;
    private JComboBox mesCbx2;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JTable table1;
    private JTextField textField1;
    private JLabel hastaLbl;
    private JLabel desdeLbl;
    private JLabel categoriaLbl;
    private JPanel panel;

    public JPanel getPanel() {
        return panel;
    }

    // MVC
    pos.presentation.productos.Model model;
    pos.presentation.productos.Controller controller;

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
        }

        this.panel.revalidate();
    }
}
}
