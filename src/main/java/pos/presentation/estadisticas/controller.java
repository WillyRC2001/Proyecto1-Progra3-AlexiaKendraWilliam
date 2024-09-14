package pos.presentation.estadisticas;

import pos.logic.Cliente;
import pos.logic.Service;


public class controller {
    View view;
    model model;

    public controller(View view, model model) {
      //  model.init(Service.instance().search()));
        this.view = view;
        this.model = model;
       // view.setController(this);
       // view.setModel(model);
    }

    public void actualizarData() {
        Rango r = model.getRango();
        int colCount = (r.annoHasta - r.annoDesde) * 12 + r.mesHasta - r.mesDesde+1;
        int rowCount = model.categorias.size();
        String[] cols = new String[colCount];
        for (int i = 0; i < colCount; i++) {
            /*nos e que va*/
            // Calcular el año y el mes correspondientes a la columna i
            int currentYear = r.annoDesde + (r.mesDesde + i - 1) / 12;
            int currentMonth = (r.mesDesde + i - 1) % 12 + 1; // El mes va de 1 a 12

            // Crear una etiqueta con el nombre del mes y el año
            String monthName = java.time.Month.of(currentMonth).name(); // Obtener el nombre del mes (en inglés)
            monthName = monthName.substring(0, 1) + monthName.substring(1).toLowerCase(); // Capitalizar el nombre

            // Asignar la etiqueta a la columna correspondiente
            cols[i] = monthName + " " + currentYear;
        }

    }


}
