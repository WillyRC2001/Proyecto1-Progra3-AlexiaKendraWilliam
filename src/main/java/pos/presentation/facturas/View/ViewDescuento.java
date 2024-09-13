package pos.presentation.facturas.View;

import com.sun.jdi.IntegerValue;
import pos.logic.Linea;
import pos.presentation.facturas.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewDescuento extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textDesc;
    private JLabel Descuento;
    private Linea linea;
    private Controller controller;

    public ViewDescuento(Linea linea, Controller controller) {
        this.linea = linea;
        this.controller = controller;
        contentPane.setPreferredSize(new Dimension(300, 150));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //textDesc.setText(Float.valueOf((float) linea.getDescuento()).toString()); //Dudas
        textDesc.setText(String.valueOf(linea.getDescuento()));

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            float desc = Float.parseFloat(textDesc.getText());
            if (desc >= 0 && desc <= 9) {
                linea.setDescuento(desc);
                if (controller != null) {
                    controller.CambiarDescuentoLinea(linea);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "El descuento debe estar entre 0 y 9", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Descuento inválido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
