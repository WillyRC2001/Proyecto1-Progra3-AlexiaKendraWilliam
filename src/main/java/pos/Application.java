package pos;

import pos.logic.Service;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }
        catch (Exception ex) {};

        window = new JFrame();
        JTabbedPane tabbedPane = new JTabbedPane();
        window.setContentPane(tabbedPane);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Service.instance().stop();
            }
        });
        pos.presentation.facturas.Model facturasModel = new pos.presentation.facturas.Model();
        pos.presentation.facturas.View.View facturasView = new pos.presentation.facturas.View.View();
        facturasController = new pos.presentation.facturas.Controller(facturasView,facturasModel);
        Icon facturasIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/factura.png"));
        tabbedPane.addTab("Factura ",facturasIcon,facturasView.getPanel());

        pos.presentation.clientes.Model clientesModel= new pos.presentation.clientes.Model();
        pos.presentation.clientes.View clientesView = new pos.presentation.clientes.View();
        clientesController = new pos.presentation.clientes.Controller(clientesView,clientesModel);
        Icon clientesIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/cliente.png"));

        tabbedPane.addTab("Clientes  ",clientesIcon,clientesView.getPanel());

        pos.presentation.cajeros.Model cajeroModel= new pos.presentation.cajeros.Model();
        pos.presentation.cajeros.View cajeroView = new pos.presentation.cajeros.View();
        cajeroController = new pos.presentation.cajeros.Controller(cajeroView,cajeroModel);
        Icon cajeroIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/cajero.png"));
        tabbedPane.addTab("Cajero  ", cajeroIcon,cajeroView.getPanel());


        pos.presentation.productos.Model productoModel= new pos.presentation.productos.Model();
        pos.presentation.productos.View productoView = new pos.presentation.productos.View();
        productoController = new pos.presentation.productos.Controller(productoView,productoModel);
        Icon productoIcon= new ImageIcon(Application.class.getResource("/pos/presentation/icons/productos.png"));
        tabbedPane.addTab("Producto  ", productoIcon,productoView.getPanel());

        pos.presentation.históricos.Model historicosModel = new pos.presentation.históricos.Model();
        pos.presentation.históricos.View historicosView = new pos.presentation.históricos.View();
        historicoController = new pos.presentation.históricos.Controller(historicosView, historicosModel);
        Icon historicosIcon = new ImageIcon(Application.class.getResource("/pos/presentation/icons/servidor.png"));
        tabbedPane.addTab("Históricos", historicosIcon, historicosView.getPanel());

        window.setSize(900,450);
        window.setResizable(false);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setIconImage((new ImageIcon(Application.class.getResource("presentation/icons/icon.png"))).getImage());
        window.setTitle("POS: Point Of Sale");
        window.setVisible(true);
    }

    //Holi //Hola
    public static pos.presentation.facturas.Controller facturasController;
    public static pos.presentation.clientes.Controller clientesController;
    public static pos.presentation.cajeros.Controller cajeroController;
    public static pos.presentation.productos.Controller productoController;
    public static pos.presentation.históricos.Controller historicoController;
    public static JFrame window;

    public final static int MODE_CREATE=1;
    public final static int MODE_EDIT=2;

    public static Border BORDER_ERROR = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.RED);
}
