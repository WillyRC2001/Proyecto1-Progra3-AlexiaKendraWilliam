package pos.logic;
import java.util.ArrayList;
import java.util.List;
public class Factura {
    private String cod;
    private List<Producto> lista_productos;
    //Constructor
    public Factura(String cod) {
        this.cod = cod;
        lista_productos = new ArrayList<Producto>();
    }
    public String getCod() {return cod;}
    public void setCod(String cod) {this.cod = cod;}
    public List<Producto> getLista_productos() {return lista_productos;}
    public void setLista_productos(List<Producto> lista_productos) {}
    //Cantidad de productos individuales de la factura
    public int cantidad() {return lista_productos.size();}
    //Metodo Ingresar producto
}
