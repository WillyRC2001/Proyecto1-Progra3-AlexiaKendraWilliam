package pos.presentation.estadisticas;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import pos.logic.Categoria;
import pos.logic.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import pos.data.LocalDateAdapter;

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
            categoriasActuales.add(p);  //Se agrega la categoria a la Lista
            model.setCategorias(categoriasActuales);
            //Se crea un vector con los nombres de la Categorias agregadas para luego almacenarlas en el model.rows
            String[] rows = new String[categoriasActuales.size()];
            for(int i = 0; i < categoriasActuales.size(); i++) {
                rows[i] = categoriasActuales.get(i).getNombre();
            }
            model.rows = rows;
        }
    }

    public void agregarTodo(){
        model.setCategorias(model.getCategoriasAll());
        //Se crea un vector con los nombres de todas las categorias para luego almacenarlas en el model.rows
        String[] rows = new String[model.getCategoriasAll().size()];
        for(int i = 0; i < model.getCategoriasAll().size(); i++) {
            rows[i] = model.getCategoriasAll().get(i).getNombre();
            System.out.println(rows[i]);
        }
        model.rows = rows;
    }

    public void eliminar(Categoria categoria) throws Exception {
        List<Categoria> categoriasActuales = model.getCategorias();
        boolean existe = categoriasActuales.contains(categoria);

        if (!existe) {
            throw new Exception("La categoría no existe.");
        } else {
            categoriasActuales.remove(categoria);
            model.setCategorias(categoriasActuales);
            //Se crea un vector con los nombres de la Categorias agregadas para luego almacenarlas en el model.rows
            String[] rows = new String[categoriasActuales.size()];
            for(int i = 0; i < categoriasActuales.size(); i++) {
                rows[i] = categoriasActuales.get(i).getNombre();
            }
            model.rows = rows;
        }
    }
    public void clear() {
       model.setCategorias(new ArrayList<>());
       model.setRows(null);
    }

    public void actualizarData() {
        Rango r = model.getRango();
        int colCount = (r.annoHasta - r.annoDesde) * 12 + r.mesHasta - r.mesDesde+1;
        int rowCount = model.categorias.size();

        //Creación de la Fila 0 con todas las columnas (Aqui es donde estoy viendo como hacer lo de las fechas, luego veo si se pude pasar a cada metodo respectivo)
        String[] cols = new String[colCount];
        for(int i = 0; i < colCount; i++){
            cols[i] = model.getRango().annoDesde + "-" + model.getRango().mesHasta; //Aqui se deberían agregar las fechas
        }

        model.cols = cols;
        float[][] data = new float[rowCount][colCount];
        // Aquí deberías llenar la matriz `data` con los valores correspondientes
        model.setData(data);
    }

    // Método para crear el gráfico
    //public ChartPanel generarGrafico() {

//        // Obtener la lista de categorías
//        List<Categoria> categorias = model.getCategorias();
//
//        if (categorias.size() >= 3) {
//            // Obtener el primer, segundo y tercer elemento
//            Categoria categoria1 = categorias.get(0);
//            Categoria categoria2 = categorias.get(1);
//            Categoria categoria3 = categorias.get(2);
//
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        LocalDate localMesDesde = model.getRango().getMesDesde();
//        l localAnioDesde = model.getRango().getAnoDesde();
//        double totalMes1 = Service.instance().getVentas(categoria1,localAnioDesde,localMesDesde);
//        double totalMes2 = Service.instance().getVentas(categoria2,localAnioDesde,localMesDesde+1);
//        double totalMes3 = Service.instance().getVentas(categoria3,localAnioDesde,localMesDesde+1);
//
//        dataset.addValue(totalMes1, "Ventas", localMesDesde);
//        dataset.addValue(totalMes2, "Ventas", localMesDesde);
//        dataset.addValue(totalMes3, "Ventas", localMesDesde);
//
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Ventas por Mes - Categoría: " + categoriaCbx.getSelectedItem(),
//                "Mes",
//                "Total Ventas",
//                dataset
//        );
//
//        return new ChartPanel(chart);
//    }
        //    Obtener la lista de categorías
//        List<Categoria> categorias = model.getCategorias();
//        // Obtener el año y mes desde el modelo
//        int anoDesde = model.getRango().getAnoDesde();
//        int mesDesde = model.getRango().getMesDesde();
//
//        // Crear LocalDate para el primer día del mes
//        LocalDate fechaInicio = LocalDate.of(anoDesde, mesDesde, 1);
//
//        // Obtener el siguiente mes y el mes después del siguiente
//        LocalDate fechaInicioMes2 = fechaInicio.plusMonths(1);
//        LocalDate fechaInicioMes3 = fechaInicio.plusMonths(2);
//
//        // Usa estos LocalDate en tu lógica
//        double totalMes1 = Service.instance().getVentas(model.getCategorias().getFirst(), fechaInicio.getYear(), fechaInicio.getMonthValue());
//        double totalMes2 = Service.instance().getVentas(model.getCategorias().get(1), fechaInicioMes2.getYear(), fechaInicioMes2.getMonthValue());
//        double totalMes3 = Service.instance().getVentas(model.getCategorias().get(2), fechaInicioMes3.getYear(), fechaInicioMes3.getMonthValue());
//
//        // Aquí puedes agregar los datos al dataset
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        dataset.addValue(totalMes1, "Ventas", "Mes " + fechaInicio.getMonth().toString());
//        dataset.addValue(totalMes2, "Ventas", "Mes " + fechaInicioMes2.getMonth().toString());
//        dataset.addValue(totalMes3, "Ventas", "Mes " + fechaInicioMes3.getMonth().toString());
//
//        // Mostrar el gráfico en un JPanel o JFrame
//        JFreeChart chart = ChartFactory.createBarChart(
//                "Ventas por Mes - Categoría: " + categorias,
//                "Mes",
//                "Total Ventas",
//                dataset
//        );
//
//        return new ChartPanel(chart);
//    }
}
