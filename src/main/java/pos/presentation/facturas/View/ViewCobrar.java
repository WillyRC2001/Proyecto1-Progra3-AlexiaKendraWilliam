package pos.presentation.facturas.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ViewCobrar extends JDialog {
    private JButton buttonOK;
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextField efectivo;
    private JTextField tarjeta;
    private JTextField cheque;
    private JTextField simpe;
    private JLabel totalLabel;
    private JPanel Pagar;
    private double total;  // Variable para almacenar el total de la factura
    private boolean pagoExitoso = false;
    public ViewCobrar(double total) {
        Pagar.setPreferredSize(new Dimension(180, 30));
        this.total=total;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        totalLabel.setText("" + String.format("%.2f", total));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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
            // Verificar si los campos están vacíos y asignar un valor por defecto de 0.0
            double efectivoPago = efectivo.getText().isEmpty() ? 0.0 : Double.parseDouble(efectivo.getText());
            double tarjetaPago = tarjeta.getText().isEmpty() ? 0.0 : Double.parseDouble(tarjeta.getText());
            double chequePago = cheque.getText().isEmpty() ? 0.0 : Double.parseDouble(cheque.getText());
            double simpePago = simpe.getText().isEmpty() ? 0.0 : Double.parseDouble(simpe.getText());

            // Calcular el total pagado
            double totalPagado = efectivoPago + tarjetaPago + chequePago + simpePago;

            // Verificar si el total pagado es suficiente
            if (totalPagado < total) {
                JOptionPane.showMessageDialog(this, "El monto pagado es insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                double cambio = totalPagado - total;
                JOptionPane.showMessageDialog(this, "Pago realizado con éxito. Cambio: " + String.format("%.2f", cambio), "Pago Completado", JOptionPane.INFORMATION_MESSAGE);
                pagoExitoso = true;
                dispose();  // Cerrar el diálogo después del pago

            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean isPagoExitoso() {
        return pagoExitoso;
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
