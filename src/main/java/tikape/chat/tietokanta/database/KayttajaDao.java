package tikape.chat.tietokanta.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tikape.chat.tietokanta.domain.Kayttaja;
import tikape.chat.tietokanta.domain.Viesti;

/**
 *
 * @author mcraty
 */
public class KayttajaDao implements Dao {

    private Database database;

    public KayttajaDao(Database database) {
        this.database = database;
    }

    @Override
    public Object findOne(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAll() throws SQLException {
        Connection connection = database.getConnection();
        String sql = "SELECT * FROM Kayttaja";
        PreparedStatement stmt = connection.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        List<Kayttaja> kayttajat = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String tunnus = rs.getString("tunnus");

            kayttajat.add(new Kayttaja(id, tunnus));
        }
        
        rs.close();
        connection.close();

        return kayttajat;
    }

    @Override
    public List findAllIn(Collection keys) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Kayttaja findByTunnus(String tunnus) throws SQLException {
        Connection connection = database.getConnection();
        String sql = "SELECT * FROM Kayttaja WHERE tunnus = '" + tunnus + "' LIMIT 1;";
        PreparedStatement stmt = connection.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        Kayttaja kayttaja = null;

        while (rs.next()) {
            int id = rs.getInt("id");
            String ktunnus = rs.getString("tunnus");
            kayttaja = new Kayttaja(id, ktunnus);
        }

        rs.close();
        connection.close();

        return kayttaja;
    }

    public Kayttaja findOrCreateByTunnus(String tunnus) throws SQLException {
        Kayttaja kayttaja = findByTunnus(tunnus);
        if (kayttaja == null) {
//            int kayttajaId = countKayttaja() + 1;
            String sql = "INSERT INTO Kayttaja (tunnus) VALUES ('" + tunnus + "');";
            database.update(sql);
        }
        kayttaja = findByTunnus(tunnus);
        
        return kayttaja;
    }

    private int countKayttaja() throws SQLException {
        Connection connection = database.getConnection();
        String sql = "SELECT COUNT(*) FROM Kayttaja;";
        PreparedStatement stmt = connection.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        int count = rs.getInt(1);

        rs.close();
        connection.close();

        return count;
    }
}
