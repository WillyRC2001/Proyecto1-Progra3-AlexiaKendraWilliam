package pos.presentation.estadisticas;

import pos.logic.Categoria;
import pos.presentation.AbstractModel;
import java.util.List;

public class model extends AbstractModel {
    List<Categoria> categoriasAll;

    List<Categoria> categorias;
    Rango rango;
    String[] rows;
    String[] cols;
    float[][]data;

}
