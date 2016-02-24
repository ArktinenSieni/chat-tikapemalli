package tikape.chat.tietokanta.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tikape.chat.tietokanta.domain.Viesti;

/**
 *
 * @author mcraty
 */
public class ViestiDao implements Dao{

    private Database database;

    public ViestiDao(Database database) {
        this.database = database;
    }

    @Override
    public List<Viesti> findAll() throws SQLException {
        Connection connection = database.getConnection();
        String sql = "SELECT Viesti.id, Viesti.sisalto, Viesti.aika, Viesti.kayttaja, Kayttaja.tunnus FROM Viesti"
                + " INNER JOIN Kayttaja ON Viesti.kayttaja = Kayttaja.id"
                + " ORDER BY aika DESC;";
        PreparedStatement stmt = connection.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        List<Viesti> viestit = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String sisalto = rs.getString("sisalto");
            Timestamp aika = rs.getTimestamp("aika");
            int kayttaja = rs.getInt("kayttaja");
            Viesti viesti = new Viesti(id, sisalto, aika, kayttaja);
            viesti.setKayttajaTeksti(rs.getString("tunnus"));
            
            viestit.add(viesti);
        }
        
        rs.close();
        connection.close();

        return viestit;
    }

    @Override
    public Object findOne(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List findAllIn(Collection keys) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Object key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void createViesti(int kayttaja, String sisalto) throws SQLException {
        int viestiId = countViesti(database.getConnection()) + 1;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Viesti viesti = new Viesti(viestiId, sisalto, now, kayttaja);
  
        String sql = "INSERT INTO Viesti (id, sisalto, aika, kayttaja) "
                + "VALUES (" + viestiId + ", '" + sisalto + "', '" + now + "', " + kayttaja + ");";
        database.update(sql);
    }
    
    private int countViesti(Connection connection) throws SQLException {
        String sql = "SELECT COUNT (*) FROM Viesti;";
        PreparedStatement stmt = connection.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        int count = rs.getInt(1);
        
        rs.close();
        connection.close();
        
        return count;
        
    }
}
