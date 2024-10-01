package pos.data;

import pos.logic.Factura;
import pos.logic.Linea;
import pos.logic.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class LineaDao {
    Database db;

    public LineaDao() {
        db = Database.instance();
    }

    public void create(Linea e) throws Exception {
        // Generar un nuevo número de línea
        String nuevoNumero = generarNumeroLinea();
        e.setCodigo(nuevoNumero);

        String sql = "insert into " +
                "Linea " +
                "(codigo, producto, factura, cantidad, descuento) " +
                "values(?,?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getCodigo());
        stm.setString(2, e.getProducto().getCodigo());
        stm.setString(3, e.getFactura().getNumero());
        stm.setInt(4, e.getCantidad());
        stm.setFloat(5, e.getDescuento());
        db.executeUpdate(stm);
    }


    public Linea read(String codigo) throws Exception {
        String sql = "select " +
                "* " +
                "from Linea l " +
                "inner join Producto p on l.producto=p.codigo " +
                "inner join Factura f on l.factura=f.codigo " +
                "where l.codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs = db.executeQuery(stm);
        ProductoDao productoDao = new ProductoDao();
        FacturaDao facturaDao = new FacturaDao();
        if (rs.next()) {
            Linea r = from(rs, "l");
            r.setProducto(productoDao.from(rs, "p"));
            r.setFactura(facturaDao.from(rs, "f"));
            return r;
        } else {
            throw new Exception("Linea NO EXISTE");
        }
    }

    public void update(Linea e) throws Exception {
        String sql = "update " +
                "Linea " +
                "set producto=?, factura=?, cantidad=?, descuento=? " +
                "where codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getProducto().getCodigo());
        stm.setString(2, e.getFactura().getNumero());
        stm.setInt(3, e.getCantidad());
        stm.setFloat(4, e.getDescuento());
        stm.setString(5, e.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Linea NO EXISTE");
        }
    }

    public void delete(Linea e) throws Exception {
        String sql = "delete " +
                "from Linea " +
                "where codigo=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Linea NO EXISTE");
        }
    }

    public List<Linea> search(Linea e) throws Exception {
        List<Linea> resultado = new ArrayList<>();
        String sql = "select * " +
                "from Linea l " +
                "inner join Producto p on l.producto=p.codigo " +
                "inner join Factura f on l.factura=f.codigo " +
                "where l.codigo like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getCodigo() + "%");
        ResultSet rs = db.executeQuery(stm);
        ProductoDao productoDao = new ProductoDao();
        FacturaDao facturaDao = new FacturaDao();
        while (rs.next()) {
            Linea r = from(rs, "l");
            r.setProducto(productoDao.from(rs, "p"));
            r.setFactura(facturaDao.from(rs, "f"));
            resultado.add(r);
        }
        return resultado;
    }

    public Linea from(ResultSet rs, String alias) throws Exception {
        Linea e = new Linea();
        e.setCodigo(rs.getString(alias + ".codigo"));
        e.setCantidad(rs.getInt(alias + ".cantidad"));
        e.setDescuento(rs.getFloat(alias + ".descuento"));
        return e;
    }

    public List<Linea> searchByFactura(Factura e) throws Exception {
        List<Linea> resultado = new ArrayList<>();
        String sql = "select * " +
                "from Linea l " +
                "inner join Producto p on l.producto=p.codigo " +
                "where l.factura like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getNumero() + "%");
        ResultSet rs = db.executeQuery(stm);
        ProductoDao productoDao = new ProductoDao();
        while (rs.next()) {
            Linea r = from(rs, "l");
            r.setProducto(productoDao.from(rs, "p"));
            resultado.add(r);
        }
        return resultado;
    }

    public synchronized String generarNumeroLinea() {
        try {
            int contadorL = getContadorL();
            updateContadorL(contadorL + 1);
            return String.format("LIN-" + "%05d", contadorL);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getContadorL() throws Exception {
        String sql = "select contadorL from Contador";
        PreparedStatement stm = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return rs.getInt("contadorL");
        } else {
            throw new Exception("No se pudo obtener el contador de líneas");
        }
    }

    private void updateContadorL(int nuevoContador) throws Exception {
        String sql = "update Contador set contadorL=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, nuevoContador);
        db.executeUpdate(stm);
    }


}
