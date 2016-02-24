package tikape.chat.tietokanta.database;


import java.sql.*;

public class Database {

    private String databaseAddress;
    private Boolean debug = true;

    public Database(String databaseAddress) throws ClassNotFoundException {
        this.databaseAddress = databaseAddress;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(databaseAddress);
    }
    
    public int update(String updateQuery) throws SQLException {
        Statement stmt = getConnection().createStatement();
        int changes = stmt.executeUpdate(updateQuery);

        if(debug) {
            System.out.println("---");
            System.out.println(updateQuery);
            System.out.println("Changed rows: " + changes);
            System.out.println("---");
        }
        stmt.close();

        return changes;
    }
}