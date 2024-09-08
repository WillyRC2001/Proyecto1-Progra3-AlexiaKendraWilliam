package pos.presentation.facturas;

import pos.Application;
import pos.logic.*;
import pos.presentation.facturas.View.View;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    View view;
    Model model;
    public Controller(View view, Model model) {
        try {
            List<Producto> productosC = new ArrayList<>();   //Revisar
            List<Producto> productosT = Service.instance().search(new Producto());
            List<Cliente> clientes = Service.instance().search(new Cliente());
            List<Cajero> cajeros = Service.instance().search(new Cajero());
            model.init(productosT, productosC, clientes, cajeros);
            this.view = view;
            this.model = model;
//            System.out.println("Productos:");
//            System.out.println(this.model.getProductosTotales());
            view.setController(this);
            view.setModel(model);
        } catch (Exception var5) {
            Exception e = var5;
            e.printStackTrace();
        }
    }
    public void search(Factura filter) throws Exception{
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
    //Metodos que desconozco si se necesitan en Factura
    //public void edit(int row){}
    //public void delete(){}
    //public void clear(){}

}
