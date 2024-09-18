package pos.presentation.facturas;

import pos.Application;
import pos.logic.*;
import pos.presentation.facturas.View.View;
import pos.presentation.facturas.View.ViewBuscar;
import pos.presentation.facturas.View.ViewCobrar;
import pos.presentation.facturas.View.ViewCantidad;
import pos.presentation.facturas.View.ViewDescuento;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Controller {
    View view;
    Model model;
    public Controller(View view, Model model) {
        try {
            List<Linea> productosC = new ArrayList<>();   //Revisar
            List<Producto> productosT = Service.instance().search(new Producto());
            List<Cliente> clientes = Service.instance().search(new Cliente());
            List<Cajero> cajeros = Service.instance().search(new Cajero());
            model.init(productosT, productosC, clientes, cajeros);
            this.view = view;
            this.model = model;
            view.setController(this);
            view.setModel(model);
        } catch (Exception var5) {
            Exception e = var5;
            e.printStackTrace();
        }
    }
    public void AgregarButtonClick(String codigo, Cliente c) {
        try {
            if (codigo.isEmpty()) {
                JOptionPane.showMessageDialog(view.getPanel(), "Código del producto no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto filter = new Producto();
            filter.setCodigo(codigo);
            List<Producto> productosEncontrados = Service.instance().search(filter);

            if (productosEncontrados.isEmpty()) {
                JOptionPane.showMessageDialog(view.getPanel(), "Producto no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto productoEncontrado = productosEncontrados.get(0);
            Linea nuevaLinea = new Linea(productoEncontrado,null,1,c.getDescuento());
            nuevaLinea.setCodigo(Service.instance().generarNumeroLinea());
            model.addLineaComprada(nuevaLinea);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    public void EliminarButtonClick(int fila) {
        try {
            if (model.getLineaComprados() == null) {
                JOptionPane.showMessageDialog(view.getPanel(), "No hay Elementos en la Lista de Compra", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.deleteLineaComprada(fila);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void CancelarButtonClick() {
        try {
            if (model.getLineaComprados() == null) {
                JOptionPane.showMessageDialog(view.getPanel(), "No hay Elementos en la Lista de Compra", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            model.deleteAllCompras(model.getLineaComprados());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void CambiarCantidad() {
        int row = view.getListaJT().getSelectedRow();
        if (row >= 0) {
            Linea lineaSeleccionada = model.getLineaComprados().get(row);
            ViewCantidad dialog = new ViewCantidad(lineaSeleccionada,this); //view cantidad constructor
            dialog.pack();  // Ajusta el tamaño de la ventana según el contenido
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(view.getPanel(), "No se ha seleccionado ningún producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void ActivarViewDescuento(){
        int row = view.getListaJT().getSelectedRow();
        if(row >= 0){
            Linea lineaSeleccionada = model.getLineaComprados().get(row);
            ViewDescuento dialog = new ViewDescuento(lineaSeleccionada, this);
            dialog.pack();
            dialog.setVisible(true);
        }else{
            JOptionPane.showMessageDialog(view.getPanel(), "No se ha seleccionado ningún producto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void CambiarDescuentoLinea(Linea lineaModificada){
        int index = model.getLineaComprados().indexOf(lineaModificada);
        if (index >= 0) {
            model.changeDescuento(index, lineaModificada.getDescuento());
        }
    }
    public void search(Producto filter) throws Exception{
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Factura());
        //model.setLis();
    }
    public void save(Factura factura) throws Exception{
        switch(model.getMode()){
            case Application.MODE_CREATE:
                Service.instance().create(factura);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(factura);
                break;
        }
        model.setCurrent(new Factura());
        search(model.getFilter());
    }

    public void Cobrar() {
        if (model.getLineaComprados().isEmpty()) {
            JOptionPane.showMessageDialog(view.getPanel(), "No hay productos en la factura.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalFactura = model.getTotal();
        ViewCobrar dialog = new ViewCobrar(totalFactura);
        dialog.pack();
        dialog.setVisible(true);
        if (!dialog.isPagoExitoso()) {
            JOptionPane.showMessageDialog(view.getPanel(), "El pago no se completó. No se ha creado la factura.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Salir si el pago no fue exitoso
        }
        Factura factura = model.getCurrent();
        factura.setNumero(Service.instance().generarNumeroFactura()); // Asignar número secuencial
        factura.setFecha(LocalDate.now());
        factura.setLista_productos(model.getLineaComprados());
        factura.setCliente((Cliente) view.getClienteJcb().getSelectedItem());
        factura.setCajero((Cajero) view.getCajeroJcb().getSelectedItem());
        try {
            // Actualizar inventario
            for (Linea linea : model.getLineaComprados()) {
                Producto producto = linea.getProducto();
                int cantidadComprada = linea.getCantidad();
                int nuevaCantidad = producto.getExistencias() - cantidadComprada;

                if (nuevaCantidad < 0) {
                    JOptionPane.showMessageDialog(view.getPanel(), "No hay suficiente stock para el producto: " + producto.getDescripcion(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Detener si no hay suficiente stock
                }

                // Actualizar las existencias del producto
                producto.setExistencias(nuevaCantidad);
                Service.instance().update(producto);  // Guardar el cambio en la base de datos
            }

            // Guardar la factura
            for (Linea linea : model.getLineaComprados()) {
                linea.setFactura(factura);
                Service.instance().create(linea);  // Guardar la línea de la factura
            }

            Service.instance().create(factura);  // Guardar la factura completa
            model.setProductosComprados(new ArrayList<>());
            model.setCurrent(new Factura());
            JOptionPane.showMessageDialog(view.getPanel(), "Factura guardada exitosamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view.getPanel(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void CambiarCantidadLinea(Linea lineaModificada) {
        int index = model.getLineaComprados().indexOf(lineaModificada);
        if (index >= 0) {
            model.changeCantidad(index, lineaModificada.getCantidad());
        }
    }

    public void BuscarProducto() {
        try {
            List<Producto> productosT = Service.instance().search(new Producto());
            ViewBuscar dialog = new ViewBuscar(productosT); // Pasamos la lista y el controlador al diálogo
            dialog.pack();  // Ajusta el tamaño de la ventana según el contenido
            dialog.setVisible(true);
            Producto productoSeleccionado = dialog.getProductoSeleccionado();
            if (productoSeleccionado != null) {
                // Llama al método para agregar el producto a la factura
                AgregarButtonClick(productoSeleccionado.getCodigo(), (Cliente) view.getClienteJcb().getSelectedItem());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getPanel(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void shown() {
        model.setCajeros(Service.instance().search(new Cajero()));
        model.setProductosTotales(Service.instance().search(new Producto()));
        model.setClientes(Service.instance().search(new Cliente()));
    }
}
