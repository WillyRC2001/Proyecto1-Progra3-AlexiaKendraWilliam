package pos.presentation.facturas.View;
import pos.logic.Linea;
import pos.logic.Producto;
import pos.presentation.facturas.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewCantidad extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel Cantidad;
    private JTextField textCant;
    private Linea linea; // Línea a modificar
    private Controller controller;
    public ViewCantidad(Linea linea, Controller controller) {
        this.linea = linea;
        this.controller = controller;
        contentPane.setPreferredSize(new Dimension(300, 150));
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // Inicializar el campo de texto con la cantidad actual
        textCant.setText(String.valueOf(linea.getCantidad()));

        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        try {
            int cantidad = Integer.parseInt(textCant.getText());
            Producto producto = linea.getProducto();  // Suponiendo que Linea tiene un método getProducto
            int existencia = producto.getExistencias();  // Suponiendo que Producto tiene un método getExistencia

            if (cantidad > 0 && cantidad <= existencia) {
                linea.setCantidad(cantidad); // Actualizar la cantidad de la línea
                if (controller != null) {
                    controller.CambiarCantidadLinea(linea); // Método que actualizará la cantidad
                }
                dispose();
            } else if (cantidad > existencia) {
                JOptionPane.showMessageDialog(this, "La cantidad no puede ser mayor a la existencia del producto.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser positiva.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onCancel() {
        dispose();
    }
}