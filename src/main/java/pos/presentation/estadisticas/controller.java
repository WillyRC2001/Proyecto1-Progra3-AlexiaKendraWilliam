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

    public void actualizarData(Categoria categoria, int count) {
        Rango r = model.getRango();
        int colCount = (r.annoHasta - r.annoDesde) * 12 + r.mesHasta - r.mesDesde+1;
        int rowCount = model.categorias.size();

        //Creación de la Fila 0 con todas las columnas (Falta agregar las fechas)
        String[] cols = new String[colCount];
        for(int i = 0; i < colCount; i++){
            cols[i] = model.getRango().annoHasta + "-" + model.getRango().mesHasta; //Aqui se deberían agregar las fechas
        }

        //Creación de la Columna 0 con todos los nombres de las Categorias que se van agregando
        String[] rows = new String[rowCount];
        //Este if es para ver si el model ya tiene categorias y que el rows las copie antes de agregar la nueva y actualizar el model.rows
        if(model.rows != null){
            for(int i = 0; i < model.rows.length; i++){
                rows[i] = model.rows[i];
            }
        }
        //Se agrega el nuevo nombre de la categoria
        rows[count] = categoria.getNombre();

        //cols[1] = "aa";

        /*Invento de chat*/
//        for (int i = 0; i < colCount; i++) {
//            int currentYear = r.annoDesde + (r.mesDesde + i - 1) / 12;
//            int currentMonth = (r.mesDesde + i - 1) % 12 + 1;
//            String monthName = java.time.Month.of(currentMonth).name();
//            monthName = monthName.substring(0, 1).toUpperCase() + monthName.substring(1).toLowerCase();
//            cols[i] = monthName + " " + currentYear;
//        }

        //Se Actualiza en el model
        model.rows = rows;
        model.cols = cols;
        float[][] data = new float[rowCount][colCount];
        // Aquí deberías llenar la matriz `data` con los valores correspondientes
        model.setData(data);
    }

}
