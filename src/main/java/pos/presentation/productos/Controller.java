package pos.presentation.productos;

import pos.Application;
import pos.logic.Producto;
import pos.logic.Categoria;
import pos.logic.Service;
import java.util.List;

public class Controller {
    View view;
    Model model;

    public Controller(View view, Model model) {
        model.init(Service.instance().search(new Producto()));
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }
    //Prueba
    public List<Categoria> getCategorias() {
        return model.getCategorias();
    }
    //Fin
    public void search(Producto filter) throws Exception {
        model.setFilter(filter);
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
        model.setList(Service.instance().search(model.getFilter()));
    }

    public void save(Producto p) throws Exception {
        switch (model.getMode()) {
            case Application.MODE_CREATE:
                Service.instance().create(p);
                break;
            case Application.MODE_EDIT:
                Service.instance().update(p);
                break;
        }
        model.setFilter(new Producto());
        search(model.getFilter());
    }

    public void edit(int row) {
        Producto p = model.getList().get(row);
        try {
            model.setMode(Application.MODE_EDIT);
            model.setCurrent(Service.instance().read(p));
        } catch (Exception ex) {}
    }

    public void delete() throws Exception {
        Service.instance().delete(model.getCurrent());
        search(model.getFilter());
    }

    public void clear() {
        model.setMode(Application.MODE_CREATE);
        model.setCurrent(new Producto());
    }




}
