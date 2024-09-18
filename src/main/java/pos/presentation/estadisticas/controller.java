package pos.presentation.estadisticas;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
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
       model.setCols(null);
    }

    public void actualizarData() {
        Rango r = model.getRango();
        int colCount = (r.annoHasta - r.annoDesde) * 12 + r.mesHasta - r.mesDesde+1;
        int rowCount = model.categorias.size();

        //Creación de la Fila 0 con todas las columnas (Aqui es donde estoy viendo como hacer lo de las fechas, luego veo si se pude pasar a cada metodo respectivo)
        String[] cols = new String[colCount];
        int mes = model.getRango().mesDesde;
        int annio = model.getRango().annoDesde;
        int k = 0;          //Contador para agregar en el vector de columnas
        int j = mes + 1;    //Para que j inicialice con el mes Desde (+1 porque el valor del mes es un menos al correspondiente)

        //Ciclo para los años y meses de las columnas
        for(int i = annio; i <= model.getRango().annoHasta; i++){
            while(j <= 12 && k < colCount){
                cols[k] = i + "-" + j;
                k++;
                j++;
            }
            if(j > 12){ j = 1;} //if para que se reinicie el contador de meses una vez llega a 12
        }


        model.cols = cols;
        float[][] data = new float[rowCount][colCount];
        // Aquí deberías llenar la matriz `data` con los valores correspondientes
        // Llenar la matriz `data` con los valores correspondientes
        for (int i = 0; i < rowCount; i++) {
            Categoria categoria = model.categorias.get(i);
            mes = model.getRango().mesDesde;
            j = mes + 1; // Reiniciar el mes para cada categoría

            for (int año = r.annoDesde; año <= r.annoHasta; año++) {
                for (int m = j; m <= 12; m++) {
                    if (k < colCount) {
                        data[i][k] = (float) Service.instance().getVentas(categoria, año, m);
                        k++;
                    }
                }
                j = 1; // Reiniciar el mes a 1 para años posteriores
            }
        }

        model.setData(data);
    }
}
