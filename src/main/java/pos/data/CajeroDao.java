package pos.data;

import pos.logic.Cajero;
import pos.logic.Categoria;
import pos.logic.Producto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class CajeroDao {
    Database db;

    public CajeroDao() {
        db = Database.instance();
    }


    public void create(Cajero e) throws Exception {
        String sql = "insert into " +
                "Cajero " +
                "(id, nombre ) " +
                "values(?,?)";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        stm.setString(2, e.getNombre());
        db.executeUpdate(stm);
    }

    public Cajero read(String codigo) throws Exception {
        String sql = "select " +
                "* " +
                "from  Cajero t " +
                "where t.id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, codigo);
        ResultSet rs = db.executeQuery(stm);
        if (rs.next()) {
            return from(rs, "t");
        } else {
            throw new Exception("CAJERO NO EXISTE");
        }
    }

    public void update(Cajero e) throws Exception {
        String sql = "update " +
                "Cajero " +
                "set nombre=?" +
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
       //stm.setString(1, e.getId());
       stm.setString(1, e.getNombre());
       stm.setString(2, e.getId());
       System.out.println(e.getNombre());
        System.out.println(e.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("CAJERO NO EXISTE");
        }
    }

    public void delete(Cajero e) throws Exception {
        String sql = "delete " +
                "from Cajero " +
                "where id=?";
        PreparedStatement stm = db.prepareStatement(sql);
        stm.setString(1, e.getId());
        int count = db.executeUpdate(stm);
        if (count == 0) {
            throw new Exception("CAJERO NO EXISTE");
        }
    }

    public List<Cajero> search(Cajero e) throws Exception {
        List<Cajero> resultado = new ArrayList<Cajero>();
//        String sql = "select * " +
//                "from " +
//                "Cajero t " +
//                "where t.id like ?";
        String sql = "select * " +
                "from " +
                "Cajero t " +
                "where t.nombre like ?";
        PreparedStatement stm = db.prepareStatement(sql);
        //stm.setString(1, "%" + e.getId() + "%");
        stm.setString(1, "%" + e.getNombre() + "%");
        ResultSet rs = db.executeQuery(stm);
        while (rs.next()) {
            Cajero r = from(rs, "t");
            resultado.add(r);
        }
        return resultado;
    }



    public Cajero from(ResultSet rs, String alias) throws Exception {
        Cajero e = new Cajero();
        e.setId(rs.getString(alias + ".id"));
        e.setNombre(rs.getString(alias + ".nombre"));
        return e;
    }



}
