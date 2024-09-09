package pos.presentation.facturas.View;
import pos.logic.Linea;
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

    public ViewCantidad(Linea linea) {
        this.linea = linea;
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
            if (cantidad > 0) {
                linea.setCantidad(cantidad); // Actualizar la cantidad de la línea
                dispose();
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