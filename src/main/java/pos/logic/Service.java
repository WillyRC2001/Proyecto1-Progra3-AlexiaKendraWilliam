package pos.logic;

import pos.data.Data;
import pos.data.XmlPersister;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;
    //private static int ultimoNumeroFactura = 1000; // Comienza en 1000 o el número que prefieras

    public static Service instance() {
        if (theInstance == null) theInstance = new Service();
        return theInstance;
    }

    private Data data;

    private Service() {
        try {
            data = XmlPersister.instance().load();
        } catch (Exception e) {
            data = new Data();
        }
    }

    public void stop() {
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Método para obtener el siguiente número de factura
    public synchronized String generarNumeroFactura() {
        try {
            updateF(data.getContadorF()+1);
            return String.format( "FAC-"+ "%05d", data.getContadorF());  // Formato con ceros a la izquierda si es necesario
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized String generarNumeroLinea() {
        try {
            updateL(data.getContadorL()+1);
            return String.format( "LIN-"+ "%05d", data.getContadorL());  // Formato con ceros a la izquierda si es necesario
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //====================================================================================================================================================



//================= CLIENTES ============

    public void create(Cliente e) throws Exception {
        Cliente result = data.getClientes().stream().filter(i -> i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result == null) data.getClientes().add(e);
        else throw new Exception("Cliente ya existe");
    }

    public Cliente read(Cliente e) throws Exception {
        Cliente result = data.getClientes().stream().filter(i -> i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Cliente no existe");
    }

    public void update(Cliente e) throws Exception {
        Cliente result;
        try {
            result = this.read(e);
            data.getClientes().remove(result);
            data.getClientes().add(e);
        } catch (Exception ex) {
            throw new Exception("Cliente no existe");
        }
    }

    public void delete(Cliente e) throws Exception {
        data.getClientes().remove(e);
    }

    public List<Cliente> search(Cliente e) {
        return data.getClientes().stream()
                .filter(i -> i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cliente::getNombre))
                .collect(Collectors.toList());
    }
    //================= CAJEROS ============

    public void create(Cajero e) throws Exception {
        Cajero result = data.getCajeros().stream().filter(i -> i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result == null) data.getCajeros().add(e);
        else throw new Exception("Cajero ya existe");
    }

    public Cajero read(Cajero e) throws Exception {
        Cajero result = data.getCajeros().stream().filter(i -> i.getId().equals(e.getId())).findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Cajero no existe");
    }

    public void update(Cajero e) throws Exception {
        Cajero result;
        try {
            result = this.read(e);
            data.getCajeros().remove(result);
            data.getCajeros().add(e);
        } catch (Exception ex) {
            throw new Exception("Cajero no existe");
        }
    }

    public void delete(Cajero e) throws Exception {
        data.getCajeros().remove(e);
    }

    public List<Cajero> search(Cajero e) {
        return data.getCajeros().stream()
                .filter(i -> i.getNombre().contains(e.getNombre()))
                .sorted(Comparator.comparing(Cajero::getNombre))
                .collect(Collectors.toList());
    }

    //================= PRODUCTOS ============
    public void create(Producto e) throws Exception {
        Producto result = data.getProductos().stream()
                .filter(i -> i.getCodigo().equals(e.getCodigo()))
                .findFirst().orElse(null);
        if (result == null) data.getProductos().add(e);
        else throw new Exception("Producto no existe");
    }

    public Producto read(Producto e) throws Exception {
        Producto result = data.getProductos().stream()
                .filter(i -> i.getCodigo().equals(e.getCodigo()))
                .findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Producto no existe");
    }

    public void update(Producto e) throws Exception {
        Producto result;
        try {
            result = this.read(e);
            data.getProductos().remove(result);
            data.getProductos().add(e);
        } catch (Exception ex) {
            throw new Exception("Producto no existe");
        }
    }

    public void delete(Producto e) throws Exception {
        data.getProductos().remove(e);
    }

    public List<Producto> search(Producto e) {
        //        return data.getProductos().stream()
//                .filter(i->i.getCodigo().contains(e.getCodigo()))
//                .sorted(Comparator.comparing(Producto::getDescripcion))
//                .collect(Collectors.toList());
//        return data.getProductos().stream()
//                .filter(i -> e.getCodigo() == null || (i.getCodigo() != null && i.getCodigo().contains(e.getCodigo())))
//                .sorted(Comparator.comparing(Producto::getCodigo))
//                .collect(Collectors.toList());

        return data.getProductos().stream()
                .filter(i -> e.getCodigo() == null || (i.getCodigo() != null && i.getCodigo().contains(e.getCodigo())))
                .sorted(Comparator.comparing(Producto::getCodigo, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }
    //================= CATEGORIA ============

    public List<Categoria> getCategorias() {
        return this.data.getCategorias();
    }

    public void create(Categoria e) throws Exception {
        Categoria result = data.getCategorias().stream()
                .filter(i -> i.getId().equals(e.getId()))
                .findFirst().orElse(null);
        if (result == null) data.getCategorias().add(e);
        else throw new Exception("Categoria no existe");
    }

    public Categoria read(Categoria e) throws Exception {
        Categoria result = data.getCategorias().stream()
                .filter(i -> i.getNombre().equals(e.getNombre()))
                .findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Categoria no existe");
    }

    public void update(Categoria e) throws Exception {
        Categoria result;
        try {
            result = this.read(e);
            data.getCategorias().remove(result);
            data.getCategorias().add(e);
        } catch (Exception ex) {
            throw new Exception("Categoria no existe");
        }
    }

    public void delete(Categoria e) throws Exception {
        data.getCategorias().remove(e);
    }

    public List<Categoria> search(Categoria e) {
        // Verifica que data.getCategorias() no sea null
        if (data.getCategorias() == null) {
            System.out.println("data.getCategorias() es null");
            return new ArrayList<>();
        }
        // Imprime las categorías antes de aplicar el filtro
        System.out.println("Categorías disponibles:");
        data.getCategorias().forEach(categoria -> System.out.println(categoria));

        //////////////////////////////////////////////////////////////////
//            return data.getCategorias().stream()
//                    .filter(i -> i.getId() != null && e.getId() != null && i.getId().contains(e.getId()))
//                    .sorted(Comparator.comparing(Categoria::getId, Comparator.nullsLast(Comparator.naturalOrder())))
//                    .collect(Collectors.toList());
        ///////////////////////////////////////////////////////
//        List<Categoria> result = data.getCategorias().stream()
//                .filter(i -> e.getId() == null || (i.getId() != null && i.getId().contains(e.getId())))
//                .peek(categoria -> System.out.println("Categoría filtrada: " + categoria)) // Imprime categorías filtradas
//                .sorted(Comparator.comparing(Categoria::getId, Comparator.nullsLast(Comparator.naturalOrder())))
//                .collect(Collectors.toList());
        /////////////////////////////////////
        // Verifica el valor de e.getId()
        System.out.println("ID de filtro: " + e.getId());
        List<Categoria> result = data.getCategorias().stream()
                .filter(i -> e.getId() == null || (i.getId() != null && i.getId().contains(e.getId())))
                .peek(categoria -> System.out.println("Categoría filtrada: " + categoria)) // Imprime categorías filtradas
                .sorted(Comparator.comparing(Categoria::getId, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
        //        List<Categoria> result = data.getCategorias().stream()
//                .filter(i -> i.getId() != null && e.getId() != null && i.getId().contains(e.getId()))
//                .peek(categoria -> System.out.println("Categoría filtrada: " + categoria)) // Imprime categorías filtradas
//                .sorted(Comparator.comparing(Categoria::getId, Comparator.nullsLast(Comparator.naturalOrder())))
//                .collect(Collectors.toList());

        // Imprime la lista final después de la ordenación
        System.out.println("Categorías después de la ordenación:");
        result.forEach(categoria -> System.out.println(categoria));

        return result;
    }
    //================= Linea ============
    public void create(Linea e) throws Exception {
        Linea result = data.getLineas().stream()
                .filter(i -> i.getCodigo().equals(e.getCodigo()))
                .findFirst().orElse(null);
        if (result == null) {
            data.getLineas().add(e);
        } else {
            throw new Exception("Linea ya existe");
        }
    }

    public Linea read(Linea e) throws Exception {
        Linea result = data.getLineas().stream().filter(i -> i.getCodigo().equals(e.getCodigo())).findFirst().orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Linea no existe");
        }
    }

    public void update(Linea e) throws Exception {
        Linea result;
        try {
            result = this.read(e);
            data.getLineas().remove(result);
            data.getLineas().add(e);
        } catch (Exception ex) {
            throw new Exception("Linea no existe");
        }
    }


    public void delete(Linea e) throws Exception {
        Linea result = data.getLineas().stream()
                .filter(i -> i.getCodigo().equals(e.getCodigo()))
                .findFirst().orElse(null);
        if (result != null) {
            data.getLineas().remove(result);
        } else {
            throw new Exception("Linea no existe");
        }
    }

    public List<Linea> search(Linea e) {
        String numeroBuscado = e.getCodigo() != null ? e.getCodigo() : "";

        return data.getLineas().stream()
                .filter(i -> i.getCodigo() != null && i.getCodigo().contains(numeroBuscado))
                .sorted(Comparator.comparing(Linea::getCodigo))
                .collect(Collectors.toList());
//        return data.getLineas().stream()
//                .filter(i -> i.getCodigo().contains(e.getCodigo()))
//                .sorted(Comparator.comparing(Linea::getCodigo))
//                .collect(Collectors.toList());
    }

    //================= Factura ============

    public List<Factura> search(Factura e) {
        String numeroBuscado = e.getNumero() != null ? e.getNumero() : "";
        return data.getFacturas().stream()
                .filter(i -> i.getNumero() != null && i.getNumero().contains(numeroBuscado))
                .sorted(Comparator.comparing(Factura::getNumero))
                .collect(Collectors.toList());
//        return data.getFacturas().stream()
//                .filter(i -> i.getNumero().contains(e.getNumero()))
//                .sorted(Comparator.comparing(Factura::getNumero))
//                .collect(Collectors.toList());
    }

    public void create(Factura e) throws Exception {
        Factura result = data.getFacturas().stream()
                .filter(i -> i.getNumero().equals(e.getNumero()))  // Assuming 'numero' is a unique identifier for Factura
                .findFirst().orElse(null);
        if (result == null) {
            data.getFacturas().add(e);
        } else {
            throw new Exception("Factura ya existe");
        }
    }


    public Factura read(Factura e) throws Exception {
        Factura result = data.getFacturas().stream()
                .filter(i -> i.getNumero().equals(e.getNumero()))
                .findFirst().orElse(null);
        if (result != null) {
            return result;
        } else {
            throw new Exception("Factura no existe");
        }
    }


    public void update(Factura e) throws Exception {
        Factura result;
        try {
            result = this.read(e);
            data.getFacturas().remove(result);
            data.getFacturas().add(e);
        } catch (Exception ex) {
            throw new Exception("Factura no existe");
        }
    }

    //=================Contador (Factura y Linea)
    public void updateF(int contador) throws Exception {
        try{
            data.setContadorF(contador);
        }catch(Exception e){
            throw new Exception("Contador no existe");
        }
    }
    public void updateL(int contador) throws Exception {
        try{
            data.setContadorL(contador);
        }catch(Exception e){
            throw new Exception("Contador no existe");
        }
    }
    //====================================================================================================================================================

}
