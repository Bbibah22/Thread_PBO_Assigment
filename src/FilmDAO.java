import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    public void insert(String judul, int harga) throws Exception {
        Connection conn = Koneksi.getConnection();
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO film (judul, harga) VALUES (?, ?)");
            ps.setString(1, judul);
            ps.setInt(2, harga);
            ps.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.close();
        }
    }

    public void delete(int id) throws Exception {
        Connection conn = Koneksi.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM film WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } finally {
            conn.close();
        }
    }

    public List<Film> getAll() throws Exception {
        List<Film> list = new ArrayList<>();
        Connection conn = Koneksi.getConnection();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM film");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Film(rs.getInt("id"), rs.getString("judul"), rs.getInt("harga")));
            }
        } finally {
            conn.close();
        }
        return list;
    }
}
