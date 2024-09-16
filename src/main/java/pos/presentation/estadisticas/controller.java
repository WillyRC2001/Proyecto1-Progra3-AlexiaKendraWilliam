package pos.presentation.estadisticas;
import pos.logic.Categoria;
import pos.logic.Service;
import java.util.ArrayList;
import java.util.List;


public class controller {
    View view;
    model model;

    public controller(View view, model model) {
        List<Categoria> categorias = Service.instance().search(new Categoria());
        Rango rango = new Rango();
        model.init(categorias, rango);
        this.view = view;
        this.model = model;
       view.setController(this);
       view.setModel(model);
    }

    public void agregar(Categoria p) throws Exception {
        List<Categoria> categoriasActuales = model.getCategorias();
        boolean existe = categoriasActuales.stream()
                .anyMatch(c -> c.equals(p));
        if (existe) {
            throw new Exception("La categoría ya existe.");
        } else {
            categoriasActuales.add(p);
            model.setCategorias(categoriasActuales);
        }
    }

    public void agregarTodo(){
        model.setCategorias(model.getCategoriasAll());
    }

    public void eliminar(Categoria categoria) throws Exception {
        List<Categoria> categoriasActuales = model.getCategorias();
        boolean existe = categoriasActuales.contains(categoria);

        if (!existe) {
            throw new Exception("La categoría no existe.");
        } else {
            categoriasActuales.remove(categoria);
            model.setCategorias(categoriasActuales);

        }
    }
    public void clear() {
       model.setCategorias(new ArrayList<>());
    }

    public void actualizarData() {
        Rango r = model.getRango();
        int colCount = (r.annoHasta - r.annoDesde) * 12 + r.mesHasta - r.mesDesde+1;
        int rowCount = model.categorias.size();
        String[] cols = new String[colCount];

        /*Invento de chat*/
        for (int i = 0; i < colCount; i++) {
            int currentYear = r.annoDesde + (r.mesDesde + i - 1) / 12;
            int currentMonth = (r.mesDesde + i - 1) % 12 + 1;
            String monthName = java.time.Month.of(currentMonth).name();
            monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();
            cols[i] = monthName + " " + currentYear;
        }

        model.cols = cols;

        float[][] data = new float[rowCount][colCount];
        // Aquí deberías llenar la matriz `data` con los valores correspondientes
        model.setData(data);
    }

}
