package pos.data;

import pos.logic.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductoDao {
    Database db;

    public ProductoDao() {
        db = Database.instance();
    }

    public void create(Producto e) throws Exception {
        String sql = "INSERT INTO Producto (codigo, descripcion, unidadMedida, precioUnitario, existencia, categoria) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getCodigo());
        stm.setString(2, e.getDescripcion());
        stm.setString(3, e.getUnidadMedida());
        stm.setDouble(4, e.getPrecioUnitario());
        stm.setInt(5, e.getExistencias());
        stm.setString(6, e.getCategoria().getId());
        db.executeUpdate(stm);
    }

    public Producto read(String codigo) throws Exception {
        String sql = "SELECT * FROM Producto t INNER JOIN Categoria c ON t.categoria = c.id WHERE t.codigo = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs = db.executeQuery(stm);
        CategoriaDao categoriaDao = new CategoriaDao();
        if (rs.next()) {
            Producto r = from(rs, "t");
            r.setCategoria(categoriaDao.from(rs, "c"));
            return r;
        } else {
            throw new Exception("Producto NO EXISTE");
        }
    }

    public void update(Producto e) throws Exception {
        String sql = "UPDATE Producto SET descripcion = ?, unidadMedida = ?, precioUnitario = ?, existencia = ?, categoria = ? WHERE codigo = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getDescripcion());
        stm.setString(2, e.getUnidadMedida());
        stm.setDouble(3, e.getPrecioUnitario());
        stm.setInt(4, e.getExistencias());
        stm.setString(5, e.getCategoria().getId());
        stm.setString(6, e.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Producto NO EXISTE");
        }
    }

    public void delete(Producto e) throws Exception {
        String sql = "DELETE FROM Producto WHERE codigo = ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getCodigo());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("Producto NO EXISTE");
        }
    }

    public List<Producto> search(Producto e) throws Exception {
        List<Producto> resultado = new ArrayList<>();
        String sql = "SELECT * FROM Producto t INNER JOIN Categoria c ON t.categoria = c.id WHERE t.codigo LIKE ?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, "%" + e.getCodigo() + "%");
        ResultSet rs = db.executeQuery(stm);
        CategoriaDao categoriaDao = new CategoriaDao();
        while (rs.next()) {
            Producto r = from(rs, "t");
            r.setCategoria(categoriaDao.from(rs, "c"));
            resultado.add(r);
        }
        return resultado;
    }

    public Producto from(ResultSet rs, String alias) throws Exception {
        Producto e = new Producto();
        e.setCodigo(rs.getString(alias + ".codigo"));
        e.setDescripcion(rs.getString(alias + ".descripcion"));
        e.setUnidadMedida(rs.getString(alias + ".unidadMedida"));
        e.setPrecioUnitario(rs.getFloat(alias + ".precioUnitario"));
        e.setExistencias(rs.getInt(alias + ".existencia"));
        return e;
    }
}
