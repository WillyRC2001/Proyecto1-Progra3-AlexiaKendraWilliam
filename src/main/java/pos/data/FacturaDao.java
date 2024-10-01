package pos.data;


import pos.logic.Categoria;
import pos.logic.Factura;
import pos.logic.Linea;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FacturaDao {
    Database db;

    public FacturaDao() {
        db = Database.instance();
    }


    public void create(Factura e) throws Exception {
        String sql = "insert into " +
                "Factura " +
                "(numero, cliente, cajero, fecha) " +
                "values(?,?,?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNumero());
        stm.setString(2, e.getCliente().getId());
        stm.setString(3, e.getCajero().getId());
        stm.setDate(4, java.sql.Date.valueOf(e.getFecha()));
        db.executeUpdate(stm);
        // Insertar las líneas de la factura
        LineaDao lineaDao = new LineaDao();
        for (Linea linea : e.getLinea()) {
            lineaDao.create(linea);
        }
    }




    public Factura read(String numero) throws Exception {
        String sql = "select " +
                "* " +
                "from Factura f " +
                "inner join Cliente c on f.cliente=c.id " +
                "inner join Cajero j on f.cajero=j.id " +
                "where f.numero=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, numero);
        ResultSet rs = db.executeQuery(stm);
        ClienteDao clienteDao = new ClienteDao();
        CajeroDao cajeroDao = new CajeroDao();
        if (rs.next()) {
            Factura r = from(rs, "f");
            r.setCliente(clienteDao.from(rs, "c"));
            r.setCajero(cajeroDao.from(rs, "j"));

            // Leer las líneas de la factura
            LineaDao lineaDao = new LineaDao();
            List<Linea> lineas = lineaDao.searchByFactura(r); // Pasar el objeto Factura
            r.setLinea(lineas);

            return r;
        } else {
            throw new Exception("Factura NO EXISTE");
        }
    }

    public void update(Factura e) throws Exception {
        String sql = "update " +
                "Factura " +
                "set cliente=?, cajero=?, fecha=? " +
                "where numero=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getCliente().getId());
        stm.setString(2, e.getCajero().getId());
        stm.setDate(3, java.sql.Date.valueOf(e.getFecha()));
        stm.setString(4, e.getNumero());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Factura NO EXISTE");
        }

        // Actualizar las líneas de la factura
        LineaDao lineaDao = new LineaDao();
        for (Linea linea : e.getLinea()) {
            lineaDao.update(linea);
        }
    }

    public void delete(Factura e) throws Exception {
        // Eliminar las líneas de la factura
        LineaDao lineaDao = new LineaDao();
        for (Linea linea : e.getLinea()) {
            lineaDao.delete(linea);
        }

        String sql = "delete " +
                "from Factura " +
                "where numero=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getNumero());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Factura NO EXISTE");
        }
    }

    public List<Factura> search(Factura e) throws Exception {
        List<Factura> resultado = new ArrayList<>();
        String sql = "select * " +
                "from Factura f " +
                "inner join Cliente c on f.cliente=c.id " +
                "inner join Cajero j on f.cajero=j.id " +
                "where f.numero like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getNumero() + "%");
        ResultSet rs = db.executeQuery(stm);
        ClienteDao clienteDao = new ClienteDao();
        CajeroDao cajeroDao = new CajeroDao();
        while (rs.next()) {
            Factura r = from(rs, "f");
            r.setCliente(clienteDao.from(rs, "c"));
            r.setCajero(cajeroDao.from(rs, "j"));

            // Leer las líneas de la factura
            LineaDao lineaDao = new LineaDao();
            List<Linea> lineas = lineaDao.searchByFactura(r);
            r.setLinea(lineas);

            resultado.add(r);
        }
        return resultado;
    }


    public Factura from(ResultSet rs, String alias) throws Exception {
        Factura e = new Factura();
        e.setNumero(rs.getString(alias + ".numero"));
        e.setFecha(rs.getDate(alias + ".fecha").toLocalDate());
        return e;
    }

    public synchronized String generarNumeroFactura() {
        try {
            int contadorF = getContadorF();
            updateContadorF(contadorF + 1);
            return String.format( "%04d", contadorF);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getContadorF() throws Exception {
        String sql = "select contadorF from Contador";
        PreparedStatement stm = db.prepareStatement(sql);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return rs.getInt("contadorF");
        } else {
            throw new Exception("No se pudo obtener el contador de facturas");
        }
    }

    private void updateContadorF(int nuevoContador) throws Exception {
        String sql = "update Contador set contadorF=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, nuevoContador);
        db.executeUpdate(stm);
    }




    public double getVentas(Categoria c, int anno, int mes) throws Exception {
        double result = 0;
        String sql = "select f.numero, f.fecha, l.producto, l.cantidad, l.descuento, p.categoria, p.precioUnitario " +
                "from Factura f " +
                "inner join Linea l on f.numero = l.factura " +
                "inner join Producto p on l.producto = p.codigo " +
                "where YEAR(f.fecha) = ? and MONTH(f.fecha) = ? and p.categoria = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setInt(1, anno);
        stm.setInt(2, mes);
        stm.setString(3, c.getId());
        ResultSet rs = db.executeQuery(stm);

        while (rs.next()) {
            double precioUnitario = rs.getDouble("precioUnitario");
            int cantidad = rs.getInt("cantidad");
            double descuento = rs.getDouble("descuento");
            result += (precioUnitario * cantidad) * (1 - descuento / 100);
        }

        return result;
    }

}
