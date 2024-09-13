package pos.presentation.estadisticas;

import pos.logic.Cliente;
import pos.logic.Service;
import pos.presentation.clientes.Model;
import pos.presentation.clientes.View;

public class controller {
    View view;
    Model model;

    public controller(View view, Model model) {
      //  model.init(Service.instance().search()));
        this.view = view;
        this.model = model;
       // view.setController(this);
       // view.setModel(model);
    }




}
